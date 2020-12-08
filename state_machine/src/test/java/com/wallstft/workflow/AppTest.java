package com.wallstft.workflow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    static ApplicationContext context = null;
    static private Logger logger = LogManager.getLogger(AppTest.class.getName());

	@DisplayName("Test shouldAnswerWithTrue()")
	@Test
    public void shouldAnswerWithTrue()
    {
//        for (String name : app_context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }

    static Action<String,String> action_handler = new Action<String, String>() {
        @Override
        public void execute(StateContext<String, String> context) {
            String msg = String.format( "Current State %s", context.getStateMachine().getState().getId());
//            System.out.println( msg );
            logger.error(msg);
        }
    };

    public static void main( String[] args ) throws Exception
    {



//        ApplicationContext context = new ClassPathXmlApplicationContext("state-machine-spring-config.xml");





//        SequentialWorkflowBuilder seqBuilder = WorkflowEngineFactory.build_sequential_workflow();

//        CustomConfigurationFactory cc = new CustomConfigurationFactory();

        logger.error("foo");

//        String[] states = { "SI", "S1", "S2", "SE" };
//        seqBuilder.states ( states );
//        seqBuilder.setInitialState("SI");
//        seqBuilder.setEndState("SE");
//
//        seqBuilder.addAction("SI", CommonWorkflowBuilder.ActionType.DO, action_handler );
//        seqBuilder.addAction("S1", CommonWorkflowBuilder.ActionType.DO, action_handler );
//        seqBuilder.addAction("S2", CommonWorkflowBuilder.ActionType.DO, action_handler );
//        seqBuilder.addAction("SE", CommonWorkflowBuilder.ActionType.DO, action_handler );
//        WorkflowEngine stateMachine = seqBuilder.build();
//        stateMachine.start();

    }
}
