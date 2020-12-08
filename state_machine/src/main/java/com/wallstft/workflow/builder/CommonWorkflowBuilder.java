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

import com.wallstft.workflow.WorkflowEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neostorm.data_structures.Triple;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.ExternalTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;

import java.util.*;
import java.util.concurrent.*;

public abstract class CommonWorkflowBuilder implements ApplicationContextAware {

    List<String> states = new ArrayList();
    List<Transition> transitions = new ArrayList();
    HashMap<String, StateAction> state_actions = new HashMap();

    CountDownLatch blocking_semaphore = null;

    ApplicationContext context = null;

    static private Logger logger = LogManager.getLogger(CommonWorkflowBuilder.class.getName());

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public enum ActionType { ENTRY, DO, EXIT };

    protected class StateAction {
        public Action<String,String> entry_action = null;
        public Action<String,String> do_action = null;
        public Action<String,String> exit_action = null;
    }



    public class EndStateHandler {

        Action<String, String> user_action = null;


        public Action<String, String> endStateAction = new Action<String, String>() {
            @Override
            public void execute(StateContext<String, String> context) {
                try {
                    if (user_action != null) {
                        user_action.execute(context);
                    }
                }
                catch ( Exception ex ) {
                    logger.error(ex);
                }
                finally {
                    String message = "Exit State.";
                    System.out.println(message);
                    System.out.flush();
                    logger.debug(message);

                    if (blocking_semaphore != null && blocking_semaphore.getCount() > 0) {
                        message = "countDown()";
                        System.out.println(message);
                        System.out.flush();
                        logger.debug(message);
                        blocking_semaphore.countDown();
                    }

                    if (exec != null) {
                        exec.shutdown();
                    }
                }
            }
        };

        public EndStateHandler ( Action<String, String> user_action ) {
            this.user_action = user_action;
        }
    }

    public Action<String, String> initStateAction = null;

    public CommonWorkflowBuilder states (String [] state_list ) {
        for ( String state : state_list ) {
            addState(state);
        }
        return this;
    }

    public CommonWorkflowBuilder states_array (List<String> state_list ) {
        for ( String state : state_list ) {
            addState(state);
        }
        return this;
    }

    public CommonWorkflowBuilder states ( List<Triple<String, ActionType, Action<String,String>>> state_list_triple) {
        for ( Triple<String, ActionType, Action<String,String>> t : state_list_triple ) {
            addState(t.getA());
            addAction( t.getA(), t.getB(), t.getC() );
        }
        return this;
    }

    public CommonWorkflowBuilder addState (String state ) {
        if ( !states.contains(state) )
            states.add(state);
        return this;
    }

    public CommonWorkflowBuilder addState (String state, ActionType action_type, Action<String,String> action_handler ) {
        if ( state != null && action_handler != null && action_type != null ) {
            if (!states.contains(state))
                states.add(state);

            addAction(state, action_type, action_handler);
        }
        return this;
    }

    public CommonWorkflowBuilder addInitialState (String state, ActionType action_type, Action<String,String> action_handler ) {
        addState(state,action_type,action_handler);
        setInitialState(state);
        return this;
    }

    public CommonWorkflowBuilder addEndState (String state, ActionType action_type, Action<String,String> action_handler ) {
        addState(state,action_type,action_handler);
        setEndState(state);
        return this;
    }


    public CommonWorkflowBuilder addState (String state, ActionType action_type, Action<String,String> action_handler, Transition[] transition_list ) {
        if ( state != null && action_handler != null && action_type != null ) {
            setCurrentState ( state, transition_list);
            if (!states.contains(state))
                states.add(state);

            addAction(state, action_type, action_handler);
            addTransition( transition_list );
        }
        return this;
    }

    private void setCurrentState ( String state , Transition[] transition_list ) {
        if ( transition_list != null ) {
            for ( Transition t : transition_list ) {
                t.setCurrent_state(state);
            }
        }
    }

    public CommonWorkflowBuilder addInitialState (String state, ActionType action_type, Action<String,String> action_handler, Transition[] transition_list ) {
        setCurrentState ( state, transition_list);
        addState(state,action_type,action_handler);
        setInitialState(state);
        addTransition( transition_list );
        return this;
    }

    public CommonWorkflowBuilder addEndState (String state, ActionType action_type, Action<String,String> action_handler, Transition[] transition_list ) {
        setCurrentState ( state, transition_list);
        addState(state,action_type,action_handler);
        setEndState(state);
        addTransition( transition_list );
        return this;
    }
    private void addTransition (Transition[] transition_list)
    {
        if ( transition_list != null ) {
            ArrayList list = new ArrayList<Transition> ();
            Collections.addAll( list, transition_list);
            addTransition(list);
        }
    }
    private void addTransition (List<Transition> transition_list)
    {
        synchronized (this) {
            if ( transition_list != null && transition_list.size()>0 ) {
                if (this.transitions == null) {
                    this.transitions = new ArrayList<>();
                }
                this.transitions.addAll(transition_list);
            }
        }
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }

    protected CommonWorkflowBuilder addTransition (String event, String current_state, String target_state )
    {
        Transition t = new Transition( event, current_state, target_state );
        if ( !transitions.contains(t)) {
            transitions.add(t);
        }
        return this;
    }

    private StateAction getStateAction ( String state ) {
        StateAction state_action = this.state_actions.get(state);
        if ( state_action == null ) {
            this.state_actions.put( state, state_action = new StateAction());
        }
        return state_action;
    }


    public CommonWorkflowBuilder addAction ( String state, ActionType type, Action<String,String> action ) {
        StateAction state_action = getStateAction( state );
        if ( state_action !=null ) {
            switch ( type ) {
                case ENTRY:
                    state_action.entry_action = action;
                    break;
                case DO:
                    state_action.do_action = action;
                    break;
                case EXIT:
                    state_action.exit_action = action;
                    break;
            }
        }
        return this ;
    }


    public CommonWorkflowBuilder addAction ( String state, ActionType type, String action_bean_name ) {
        Action<String,String> action = (Action<String, String>)context.getBean(action_bean_name);
        StateAction state_action = getStateAction( state );
        if ( state_action !=null ) {
            switch ( type ) {
                case ENTRY:
                    state_action.entry_action = action;
                    break;
                case DO:
                    state_action.do_action = action;
                    break;
                case EXIT:
                    state_action.exit_action = action;
                    break;
            }
        }
        return this ;
    }

    public CommonWorkflowBuilder addStateAction ( String state, Action<String,String> entry_action, Action<String,String> do_action, Action<String,String> exit_action )
    {
        addAction( state, ActionType.ENTRY, entry_action );
        addAction( state, ActionType.DO,    do_action    );
        addAction( state, ActionType.EXIT,  exit_action  );
        return this;
    }

    String initialState = null;
    String endState = null;
    EndStateHandler endStateHandler = null;
    SyncTaskExecutor taskExecutor = null;
    ConcurrentTaskScheduler scheduler = null;
    ScheduledExecutorService exec = null;


    class MyTaskExecutor extends SyncTaskExecutor {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        @Override
        public void execute(Runnable runnable) {
            if ( pool != null ) {
                pool.execute(runnable);
            }
        }
    }


    protected void setExecutor (StateMachineBuilder.Builder<String, String> builder) throws Exception
    {
        logger.info("Setting task executor()");
        taskExecutor = new SyncTaskExecutor();
        builder.configureConfiguration().withConfiguration().taskExecutor( taskExecutor );

        logger.info("Setting scheduler executor()");
        exec = Executors.newScheduledThreadPool(1);
        scheduler = new ConcurrentTaskScheduler(exec);
        scheduler.setScheduledExecutor(exec);

        builder.configureConfiguration().withConfiguration().taskScheduler(scheduler);

    }

    protected void build_states(StateMachineBuilder.Builder<String, String> builder) throws Exception {

        StateConfigurer<String, String> state_configurer = builder.configureStates().withStates();
        state_configurer.initial(initialState);

        HashMap<String, Object> data_parameters = new HashMap<>();
        if (states != null && states.size() > 0) {
            for (String state : states) {
                if ( state != null && initialState != null && state.equals(initialState)) {
                    continue;
                }
                StateConfigurer<String, String> sc = state_configurer.state(state);
                StateAction state_action = state_actions.get(state);
                if (sc != null && state_action != null) {
                    if (state_action.entry_action != null)
                        sc.stateEntry(state, state_action.entry_action);
                    if (state_action.do_action != null) {
                        if ( state != null && state.equals(endState)) {
                            endStateHandler = new EndStateHandler(state_action.do_action);
                        }
                        else {
                            sc.stateDo(state, state_action.do_action);
                        }
                    }
                    if (state_action.entry_action != null)
                        sc.stateExit(state, state_action.exit_action);
                }
            }
        }

        StateConfigurer<String, String> sc = state_configurer.end(endState);
        sc.stateDo(endState, endStateHandler.endStateAction);
    }

    protected void build_transitions (StateMachineBuilder.Builder<String, String> builder) throws Exception
    {
        ExternalTransitionConfigurer<String, String> transition_configurer = builder.configureTransitions().withExternal();

        if ( transitions != null ) {
            for ( int i =0; i<transitions.size() ; i++, transition_configurer = transition_configurer.and().withExternal() ) {
                Transition t = transitions.get(i);
                if ( t != null ) {
                    String event = t.event;
                    String source_node = t.current_state;
                    String target_node = t.target_state;
                    transition_configurer = transition_configurer.source(source_node).target(target_node).event(event);
                }
            }
        }
    }

    public WorkflowEngine buildBackgroundStateMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        setExecutor(builder);
        build_states( builder );
        build_transitions( builder );

        StateMachine stateMachine = builder.build();
        return new WorkflowEngine(stateMachine);
    }

    public WorkflowEngine buildBlockingStateMachine() throws Exception {


        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();
        setExecutor(builder);
        build_states( builder );
        build_transitions( builder );

        StateMachine stateMachine = builder.build();
        blocking_semaphore = new CountDownLatch(1);
        return new WorkflowEngine(stateMachine, blocking_semaphore);
    }
}
