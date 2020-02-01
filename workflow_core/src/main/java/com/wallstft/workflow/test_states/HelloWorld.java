package com.wallstft.workflow.test_states;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.wallstft.workflow.core.TaskDelegate;
import com.wallstft.workflow.schema.StateSchema;

public class HelloWorld implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
	}

}
