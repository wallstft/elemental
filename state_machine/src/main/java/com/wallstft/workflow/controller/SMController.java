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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
public class SMController implements ApplicationContextAware {

    ApplicationContext context = null;

    //    @Autowired
    private StateMachine<String, String> stateMachine;

    private Logger logger = LogManager.getLogger(SMController.class.getName());

    @RequestMapping(value="/start", method= RequestMethod.POST)
    @ResponseBody
    String start_workflow( @RequestBody String json  ) throws Exception {
        run_state_machine_with_uml();

        return String.format("<center><h1>State Machine has started.  State = %s</h1></center>", stateMachine.getState().getId());
    }

    @RequestMapping(value="/event", method= RequestMethod.POST )
    @ResponseBody
    String transition_event( @RequestBody String json  ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        if ( json != null ) {
            JsonNode node = mapper.readTree(json);

            if ( node != null ) {
                JsonNode event = node.get("event");
                if ( event != null && event.isTextual() ) {
                    String event_name = event.asText();
                    stateMachine.sendEvent(event_name);
                    System.out.println(stateMachine.getState());
                }
            }
        }

        return String.format("<center><h1>Transition E1.  New State = %s</h1></center>", stateMachine.getState().getId());
    }

    public void run_state_machine_with_uml() throws Exception
    {
        JsonStateMachineBuilder builder = (JsonStateMachineBuilder)context.getBean("JsonStateMachineBuilder", "classpath:sm.json");
        stateMachine = builder.build();
        stateMachine.start();
    }

    public void run_state_machine() throws Exception
    {
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


        System.out.println("Hello World");
        stateMachine.start();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
