//package config;
//
///*
//   Copyright 2018 Wall Street Fin Tech
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//
//
//    */
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.statemachine.action.Action;
//import org.springframework.statemachine.config.EnableStateMachine;
//import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
//import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
//import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
//
//import java.util.Arrays;
//import java.util.HashSet;
//
//@Configuration
//@EnableStateMachine
//public class SimpleStateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {
//
//
//
//    @Bean
//    public Action<String, String> errorAction() {
//        return ctx -> System.out.println(
//                "Error " + ctx.getSource().getId() + ctx.getException());
//    }
//
//
//    @Bean
//    public Action<String, String> initAction() {
//        return ctx -> System.out.println(ctx.getTarget().getId());
//    }
//
//    @Bean
//    public Action<String, String> executeAction() {
//        return ctx -> System.out.println("Do" + ctx.getTarget().getId());
//    }
//
//    @Override
//    public void configure(StateMachineStateConfigurer<String, String> states)
//            throws Exception {
//
//        states
//                .withStates()
//                .initial("SI")
//                .end("SF")
//                .state ("S1")
//                .state ("S2")
//                .state("S3", executeAction(), errorAction());
//
//    }
//
//    @Override
//    public void configure(
//            StateMachineTransitionConfigurer<String, String> transitions)
//            throws Exception {
//
//        transitions.withExternal()
//                .source("SI").target("S1").event("E1").action(initAction()).and()
//                .withExternal()
//                .source("S1").target("S2").event("E2").and()
//                .withExternal()
//                .source("S2").target("SF").event("end");
//    }
//}