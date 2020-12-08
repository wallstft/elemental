package org.neostorm;

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
import org.neostorm.sequential_workflow_test.SequentialWorkflowTest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration( locations = { TestUtil.filename })
public class TestUtil implements ApplicationContextAware {
    static public final String filename = "state-machine-spring-config.xml";

    ApplicationContext context = null;

    static protected Logger logger = LogManager.getLogger(SequentialWorkflowTest.class.getName());

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}