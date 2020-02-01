package com.wallstft.workflow.engine;

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

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WorkflowEngine {


    String config_filename = "activiti.cfg.xml";
    ProcessEngine processEngine = null;

    public WorkflowEngine() {
    }

    public void addWorkflow ( String workflow_bpmn_filename, String workflow_name  )
    {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentQuery dq = repositoryService.createDeploymentQuery().deploymentName(workflow_name);
        if ( dq == null || dq.count() == 0 ) {
            repositoryService.createDeployment()
                    .addClasspathResource(workflow_bpmn_filename)
                    .deploy();
        }
    }

    public void buildProcessEngine(String bpmn_filename, String workflow_name)
    {
        if ( processEngine == null ) {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream(config_filename);
            if ( resource != null ) {
	            ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(resource);
	            config.setAsyncExecutorActivate(true);
	            
	            processEngine = config.buildProcessEngine();
	            addWorkflow(bpmn_filename, workflow_name);
            }
            else {
            	System.out.println(String.format("Could not file configuration file = %s", config_filename));
            }
        }
    }

    public WorkflowInstance start (String bpmn_filename, String workflow_name, Map<String, Object> variables ) {
        WorkflowInstance instance = null;
        try {
        	
        	buildProcessEngine(bpmn_filename, workflow_name);

            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workflow_name, variables);

            instance =  new WorkflowInstance(processInstance);
//            TaskService taskService = processEngine.getTaskService();

        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return instance;
    }

}
