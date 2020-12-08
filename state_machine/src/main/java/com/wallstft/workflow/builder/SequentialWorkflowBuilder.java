package com.wallstft.workflow.builder;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.ExternalTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

import java.util.HashMap;

public class SequentialWorkflowBuilder extends CommonWorkflowBuilder {

    private final String Next = "NEXT";

    static private Logger logger = LogManager.getLogger(SequentialWorkflowBuilder.class.getName());
    @Override
    protected void build_states(StateMachineBuilder.Builder<String, String> builder) throws Exception {

        StateConfigurer<String, String> state_configurer = builder.configureStates().withStates();
        if ( initialState != null )
            state_configurer = state_configurer.initial(initialState);

        HashMap<String, Object> data_parameters = new HashMap<>();
        if (states != null && states.size() > 0) {
            for (String state : states) {
                StateConfigurer<String, String> sc = null;
                sc = state_configurer.state(state);
                StateAction state_action = state_actions.get(state);
                if (sc != null && state_action != null) {
                    if (state_action.entry_action != null)
                        sc.stateEntry(state, state_action.entry_action);
                    if (state_action.do_action != null) {
                        if ( state != null && state.equals(endState)) {
                            endStateHandler = new EndStateHandler(state_action.do_action);
                        }
                        else {
                             sc.stateDo(state, new Action<String, String>() {
                                @Override
                                public void execute(StateContext<String, String> context) {
                                    try {
                                        if (state_action != null && state_action.do_action != null) {
                                            state_action.do_action.execute(context);
                                        }
                                        if (context != null) {
                                            context.getStateMachine().sendEvent(Next);
                                        }
                                    }
                                    catch ( Exception ex ) {
                                        logger.error(ex);
                                    }
                                }
                            });
                        }
                    }
                    if (state_action.exit_action != null)
                        sc.stateExit(state, state_action.exit_action);
                }
            }
        }

        StateConfigurer<String, String> sc = state_configurer.end(endState);
        if (endStateHandler != null ) {
            sc.stateDo(endState, endStateHandler.endStateAction);
        }
    }

    @Override
    protected void build_transitions(StateMachineBuilder.Builder<String, String> builder) throws Exception {
        ExternalTransitionConfigurer<String, String> transition_configurer = builder.configureTransitions().withExternal();

        String source_state = null;
        String target_state = null;
        if ( states != null && states.size()>0 ) {
            String first_state = states.get(0);
            transition_configurer = transition_configurer.source(initialState).target(first_state).event(Next);
            if (states != null && states.size() > 1) {
                for (int i = 1; i < states.size(); i++, transition_configurer = transition_configurer.and().withExternal()) {
                    source_state = states.get(i - 1);
                    target_state = states.get(i);
                    if (source_state != null) {
                        transition_configurer = transition_configurer.source(source_state).target(target_state).event(Next);
                    }
                }
            }
            transition_configurer = transition_configurer.source(target_state).target(endState).event(Next);
        }
        else {
            transition_configurer = transition_configurer.source(initialState).target(endState).event(Next);
        }
    }
}
