package org.neostorm.state_machine_test;

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
import com.wallstft.workflow.WorkflowEngineFactory;
import com.wallstft.workflow.builder.CommonWorkflowBuilder;
import com.wallstft.workflow.builder.SequentialWorkflowBuilder;
import com.wallstft.workflow.builder.StateMachineWorkflowBuilder;
import com.wallstft.workflow.builder.Transition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neostorm.TestUtil;
import org.neostorm.Util;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.LifecycleObjectSupport;

public class StateMachineWorkflowTest extends TestUtil {

    static Action<String,String> action_handler = new Action<String, String>() {
        @Override
        public void execute(StateContext<String, String> context) {
            State<String,String> source_state = context.getSource();
            State<String,String> target_state = context.getTarget();
            String msg = String.format( "Source State = %s, Target State %s", ((source_state != null ) ? source_state.getId() : ""), ((target_state != null ) ? target_state.getId() : "") );
//            System.out.println( msg );
            logger.info(msg);
            logger.error("Sleeping for 5 seconds");
            Util.sleep(5000 );
        }
    };


    @DisplayName("StateMachine Workflow Test")
    @Test
    public  void state_machine_workflow_test() throws Exception
    {

        StateMachineWorkflowBuilder smBuilder = WorkflowEngineFactory.build_state_machine_workflow();

        smBuilder.addInitialState("SI", CommonWorkflowBuilder.ActionType.DO, action_handler, new Transition[] {
                new Transition("Move.SI.to.S1", "S1" ),
                new Transition("STOP", "SE" )
        });
        smBuilder.addState("S1", CommonWorkflowBuilder.ActionType.DO, action_handler, new Transition[] {
                new Transition("Move.S1.to.S2",  "S2" ),
                new Transition("Move.S1.to.S1", "S1" ),
                new Transition("STOP", "SE" )
        } );
        smBuilder.addState("S2", CommonWorkflowBuilder.ActionType.DO, action_handler, new Transition[] {
                new Transition("Move.S2.to.SE",  "SE" ),
                new Transition("Move.S2.to.S1",  "S1" ),
                new Transition("STOP",  "SE" )
        } );
        smBuilder.addEndState("SE", CommonWorkflowBuilder.ActionType.DO, action_handler );



        WorkflowEngine stateMachine = smBuilder.buildBlockingStateMachine();
        stateMachine.start();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Util.sleep(5000);
                logger.info("Transition from SI to S1");
                stateMachine.sendEvent ("Move.SI.to.S1");
                Util.sleep(5000);
                stateMachine.sendEvent ("Move.S1.to.S2");
                logger.info("two events sent");
                Util.sleep(5000);
                stateMachine.sendEvent ("Move.S2.to.S1");
                Util.sleep(5000);
                stateMachine.sendEvent ("Move.S1.to.S1");
                logger.info("four events sent");
                Util.sleep(5000);
                stateMachine.sendEvent ("Move.S1.to.S2");
                Util.sleep(5000);
                stateMachine.sendEvent ("Move.S2.to.SE");
                logger.info("should be done");
            }
        });
        t.run();

        stateMachine.await();
        logger.info("await() returned");

   }

}
