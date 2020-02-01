package com.wallstft.workflow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wallstft.workflow.engine.WorkflowEngine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
	@DisplayName("Test shouldAnswerWithTrue()")
	@Test
    public void shouldAnswerWithTrue()
    {
    	String workflow_name = "myFirstWF";
    	HashMap<String, Object> variables = new HashMap();
    	
    	
    	WorkflowEngine engine = new WorkflowEngine();
    	engine.start("TestWorkflowProcess.bpmn", workflow_name, variables);
    }
}
