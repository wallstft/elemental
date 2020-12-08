package org.neostorm.sequential_workflow_test;

import com.wallstft.workflow.WorkflowEngine;
import com.wallstft.workflow.WorkflowEngineFactory;
import com.wallstft.workflow.builder.CommonWorkflowBuilder;
import com.wallstft.workflow.builder.SequentialWorkflowBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neostorm.TestUtil;
import org.neostorm.Util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit test for simple App.
 */

public class SequentialWorkflowTest extends TestUtil
{


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

    @DisplayName("Sequential Workflow Test")
	@Test
    public  void sequential_test() throws Exception
    {

//        ApplicationContext context = new ClassPathXmlApplicationContext("state-machine-spring-config.xml");
        SequentialWorkflowBuilder seqBuilder = WorkflowEngineFactory.build_sequential_workflow();

        logger.error("foo");

        seqBuilder.addInitialState("SI", CommonWorkflowBuilder.ActionType.DO, action_handler );
        for ( int i=0; i<10; i++ ) {
            String name = String.format("S%d", i);
            seqBuilder.addState ( name , CommonWorkflowBuilder.ActionType.DO, action_handler );
        }
        seqBuilder.addEndState("SE", CommonWorkflowBuilder.ActionType.DO, action_handler );

        WorkflowEngine stateMachine = seqBuilder.buildBlockingStateMachine();
        stateMachine.start();

        logger.info("WorkflowEngine.start() has returned");

        Util.sleep(2000);

        int i =0;
        LifecycleObjectSupport n = null;

        logger.info("pre await()");
        stateMachine.await();
        logger.info("post await()");
    }

}
