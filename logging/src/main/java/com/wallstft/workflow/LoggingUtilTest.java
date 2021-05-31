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
import org.junit.jupiter.api.Test;

public class LoggingUtilTest {

    private Logger logger = LogManager.getLogger(LoggingUtilTest.class.getName());


    @Test
    public void logging_test()
    {
        try {
            for ( int i =0; i<10; i++ ) {
                logger.info(String.format("log message %d",  i));
            }

            LoggerUtil.addAppender ( System.out, "console2");

            for ( int i =10; i<20; i++ ) {
                if ( i == 14 ) {
                    LoggerUtil.removeAppender ( "console");
                }
                logger.info(String.format("log message %d",  i));
            }
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {

        LoggingUtilTest t = new LoggingUtilTest();
        t.logging_test();


//        logger.debug("Hello from Log4j 2");
//
//        // in old days, we need to check the log level log to increase performance
//        /*if (logger.isDebugEnabled()) {
//            logger.debug("{}", getNumber());
//        }*/
//
//        // with Java 8, we can do this, no need to check the log level
//        while (true)//test rolling file
//            logger.debug("hello {}", () -> getNumber());

    }

}
