package com.wallstft.workflow.core;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallstft.workflow.consts.WorkflowConsts;
import com.wallstft.workflow.schema.StateSchema;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class TaskDelegate implements JavaDelegate {

    ErrorHandlerInterface errorHandler = null;

    final public ErrorHandlerInterface getErrorHandler() {
        return errorHandler;
    }

    final public void setErrorHandler(ErrorHandlerInterface errorHandler) {
        this.errorHandler = errorHandler;
    }

    private void report_error (String message, String stack_trace )
    {
        if ( errorHandler != null ) {
            errorHandler.report_error(message, stack_trace);
        }
    }

    private boolean validate_schema ( String json_schema, String json )
    {
        try {
            JSONObject jsonSchema = new JSONObject(new JSONTokener(json_schema));
            JSONObject jsonSubject = new JSONObject(new JSONTokener(json));

            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonSubject);
        }
        catch ( ValidationException ex ) {
            return false;
        }

        return true;
    }

    @Override
    final public void execute(DelegateExecution delegateExecution) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String,Object> map = (HashMap)delegateExecution.getVariables();
            String json = null;
            if ( map !=null && map.get(WorkflowConsts.JsonParameters) != null) {
                json = (String)map.get(WorkflowConsts.JsonParameters);
            }
            StateSchema input_schema  = getInputParameterSchema();
            StateSchema output_schema = getOutputParameterSchema();
            if ( input_schema == null ) {
                report_error(String.format("Input StateSchema for workflow state %s must not be null", task_name()), null);
                return ;
            }
            if ( output_schema == null ) {
                report_error(String.format("Output StateSchema for workflow state %s must not be null", task_name()), null);
                return ;
            }
            ArrayList<ErrorMessage> message_list = new ArrayList<>();
            if ( validate_schema ( input_schema.getSchema(), json ) ) {
                JsonNode json_node= mapper.readTree(json);
                json_node = pre_execute_task(json_node);
                json_node = execute_task(json_node);
                json_node = post_execute_task(json_node);
                String return_json = null;
                if ( json_node != null ) {
                    return_json = mapper.writeValueAsString(json_node);
                }
                if ( validate_schema( output_schema.getSchema(), return_json)) {
                    map.put(WorkflowConsts.JsonParameters, return_json);
                }
            }
            else {
                for ( ErrorMessage m : message_list ) {
                    report_error(m.message, m.stack_trace );
                }
            }
        }
        catch ( Exception ex )
        {
            if ( ex != null ) {
                String message = ex.getMessage();
                String stacktrace = ExceptionUtils.getStackTrace(ex);
                report_error( message, stacktrace );
            }
        }
    }



    public JsonNode pre_execute_task (JsonNode parameters) throws Exception{
        return parameters;
    }

    public JsonNode post_execute_task(JsonNode parameters) throws Exception {
        return parameters;
    }

    abstract public String task_name ();
    abstract public JsonNode execute_task ( JsonNode parameters ) throws Exception;
    abstract public StateSchema getInputParameterSchema  ();
    abstract public StateSchema getOutputParameterSchema ();
}
