package com.wallstft.workflow;

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
import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class WorkflowEngine {

    private Logger logger = LogManager.getLogger(WorkflowEngine.class.getName());

    CountDownLatch blocking_semaphore = null;
    StateMachine stateMachine = null;


    public WorkflowEngine(StateMachine stateMachine, CountDownLatch blocking_semaphore )
    {
        this.blocking_semaphore = blocking_semaphore;
        this.stateMachine = stateMachine;
    }
    public WorkflowEngine(StateMachine stateMachine)
    {
        this.stateMachine = stateMachine;
    }

    public void start()
    {
        try {
            stateMachine.start();
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

    public void sendEvent (String event_name )
    {
        if ( this.stateMachine != null )
            this.stateMachine.sendEvent(event_name);
    }

    public void await()
    {
        try {
            if (blocking_semaphore != null && blocking_semaphore.getCount()>0) {
                blocking_semaphore.await();
            }
        }
        catch ( Exception ex ) {
            logger.error(ex);
        }
    }

}
