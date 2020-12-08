package com.wallstft.workflow.controller;

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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.wallstft.workflow.action_util.CustomAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.ExternalTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

import java.io.File;
import java.util.HashMap;

public class JsonStateMachineBuilder implements ApplicationContextAware {

    ApplicationContext context = null;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private Logger logger = LogManager.getLogger(JsonStateMachineBuilder.class.getName());


    public JsonStateMachineBuilder()
    {
        int i=0;
    }

    File json_file = null;
    JsonStateMachineBuilder(String path )
    {
        json_file = resolveResource(path);
    }

    private File resolveResource( String path ) {
        File f = null;
        try {
            Resource r = path != null ? resourceLoader.getResource(path) : null;
            f = r.getFile();
        }
        catch ( Exception x ) {
            x.printStackTrace();
        }

        return f;
    }

    public StateMachine<String, String> build_manual() throws Exception
    {
        StateMachine<String, String> stateMachine = null;
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        builder.configureStates().withStates()
                .initial("SI")
                .state("S1")
                .end("SF");

        builder.configureTransitions()
                .withExternal()
                .source("SI").target("S1").event("E1")
                .and().withExternal()
                .source("S1").target("SF").event("E2");

        stateMachine = builder.build();

        return stateMachine;
    }

    Action<String, String> endStateAction = new Action<String,String> ()
    {
        @Override
        public void execute(StateContext<String, String> context) {
            int i =0;
        }
    };


    public StateMachine<String, String> build() throws Exception
    {
        StateMachine<String, String> stateMachine = null;
        if ( json_file != null && json_file.exists() ) {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json_file);
            JsonNode initialState = root.get("initialState");
            JsonNode endState = root.get("endState");
            JsonNode states  = root.get("states");

            JsonNode transitions  = root.get("transitions");

            StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
            StateConfigurer<String, String> state_configurer = builder.configureStates().withStates();
            state_configurer.initial(initialState.asText());

            HashMap<String,Object> data_parameters = new HashMap<>();
            if ( states != null && states.isArray() ) {
                ArrayNode list = (ArrayNode)states;
                for ( JsonNode state : list ) {
                    StateConfigurer<String, String> sc = state_configurer.state(state.asText());
                    if ( sc != null ) {
                        CustomAction action = (CustomAction)context.getBean("CustomAction", data_parameters);
                        sc.stateEntry(state.asText(), action.getAction() );
                    }
                    if ( sc != null ) {
                        CustomAction action = (CustomAction)context.getBean("CustomAction", data_parameters);
                        sc.stateExit(state.asText(),  action.getAction()  );
                    }
                    if ( sc != null ) {
                        CustomAction action = (CustomAction)context.getBean("CustomAction", data_parameters);
                        sc.stateDo(state.asText(),    action.getAction()  );
                    }
                }
            }

            StateConfigurer<String, String> sc = state_configurer.end(endState.asText());
            sc.stateDo (endState.asText(), endStateAction);

            ExternalTransitionConfigurer<String, String> transition_configurer = builder.configureTransitions().withExternal();

            if ( transitions != null && transitions.isArray() ) {
                ArrayNode transition_list = (ArrayNode)transitions;
                for ( int i =0; i<transition_list.size() ; i++, transition_configurer = transition_configurer.and().withExternal() ) {
                    JsonNode tlist = transition_list.get(i);
                    if ( tlist != null && tlist.isArray() ) {
                        JsonNode event = tlist.get(0);
                        JsonNode source_node = tlist.get(1);
                        JsonNode target_node = tlist.get(2);
                        transition_configurer = transition_configurer.source(source_node.asText()).target(target_node.asText()).event(event.asText());
                    }
                }
            }

            stateMachine = builder.build();
        }

        return stateMachine;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
