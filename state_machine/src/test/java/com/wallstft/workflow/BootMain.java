package com.wallstft.workflow;

import com.wallstft.workflow.controller.SMController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class BootMain
{

    private Logger logger = LogManager.getLogger(BootMain.class.getName());


    public static void main( String[] args ) throws Exception
    {
        ApplicationContext app_context = SpringApplication.run(BootMain.class, args);

//        for (String name : app_context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }

}
