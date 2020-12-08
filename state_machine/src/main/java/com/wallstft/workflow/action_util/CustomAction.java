package com.wallstft.workflow.action_util;

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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.HashMap;

public class CustomAction implements ApplicationContextAware, StateAction {

    HashMap<String,Object> data_container = null;
    ApplicationContext context = null;
    Action<String, String> action = new Action<String,String>()
    {
        @Override
        public void execute(StateContext<String, String> context) {
            if ( context != null ){
                execute_action( context, data_container );
                trigger( context );
            }
        }
    };

    public HashMap<String,Object> getData_container() {
        return data_container;
    }

    public void setData_container(HashMap<String,Object> data_container) {
        this.data_container = data_container;
    }

    public Action<String, String> getAction() {
        return action;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public CustomAction() {
    }

    public CustomAction(HashMap<String,Object> data) {
        this.data_container = data;
    }


    @Override
    public void execute_action(StateContext<String, String> context, HashMap<String, Object> data_parameters) {}

    @Override
    public void trigger(StateContext<String, String> context) {
        //                context.getStateMachine().sendEvent("E1");
    }
}
