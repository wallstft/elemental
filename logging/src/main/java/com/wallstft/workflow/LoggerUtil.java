package com.wallstft.workflow;

import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 * Unit test for simple App.
 */
public class LoggerUtil
{
	
    private Logger logger = LogManager.getLogger(LoggerUtil.class.getName());
    
    private String[] messages = new String[] {
        "Hello, World",
        "Goodbye Cruel World",
        "You had me at hello"
    };
    private Random rand = new Random(1);
 
    public void setMessages(String[] messages) {
        logger.traceEntry(new JsonMessage(messages));
        this.messages = messages;
        logger.traceExit();
    }
 
    public String[] getMessages() {
        logger.traceEntry();
        logger.traceExit();
        return messages;
    }
 
    public String retrieveMessage() {
        logger.traceEntry();
 
        String testMsg = getMessage(getKey());
 
        return logger.traceExit(testMsg);
    }
 
    public void exampleException() {
    	 logger.traceEntry();
        try {
            String msg = messages[messages.length];
            logger.error("An exception should have been thrown");
        } catch (Exception ex) {
            logger.catching(ex);
        }
        logger.traceExit();
    }
 
    public String getMessage(int key) {
        logger.traceEntry();
 
        String value = messages[key];
 
        return logger.traceExit(value);
    }
 
    private int getKey() {
        logger.traceEntry();
        int key = rand.nextInt(messages.length);
        return logger.traceExit(key);
    }
      
//    @Test
//    public void shouldAnswerWithTrue()
//    {
////        AppTest service = new AppTest();
////        service.retrieveMessage();
////        service.retrieveMessage();
////        service.exampleException();
//
//    	try {
//	    	for ( int i =0; i<10; i++ ) {
//	    		logger.info(String.format("log message %d",  i));
//	    	}
//
////	    	testBuilder();
//	    	addAppender ( System.out, "console");
//
//	    	for ( int i =10; i<20; i++ ) {
//	    		if ( i == 14 ) {
//	    	    	removeAppender ( "console");
//	    		}
//	    		logger.info(String.format("log message %d",  i));
//	    	}
//    	}
//    	catch ( Exception ex ) {
//    		ex.printStackTrace();
//    	}
//    }
    
    static private void updateLoggers(final Appender appender, final Configuration config) {
        final Level level = null;
        final Filter filter = null;
        for (final LoggerConfig loggerConfig : config.getLoggers().values()) {
            loggerConfig.addAppender(appender, level, filter);
        }
        config.getRootLogger().addAppender(appender, level, filter);
    }
    
    static public void addAppender(final OutputStream outputStream, final String outputStreamName) {
        final LoggerContext context = LoggerContext.getContext(false);
        final Configuration config = context.getConfiguration();
        
        Layout layout = PatternLayout.newBuilder()
        .withPattern(PatternLayout.SIMPLE_CONVERSION_PATTERN)
        .withPatternSelector(null)
        .withConfiguration(config)
        .withRegexReplacement(null)
        .withCharset(null)
        .withAlwaysWriteExceptions(true)
        .withNoConsoleNoAnsi(true)
        .withHeader(null)
        .withFooter(null)
        .build();
        
        Filter f = ThresholdFilter.createFilter(Level.TRACE, Result.ACCEPT, Result.DENY);
        
        
        final Appender appender = OutputStreamAppender.createAppender(layout, f, outputStream, outputStreamName, false, true);
        appender.start();
        config.addAppender(appender);

       
        updateLoggers(appender, config);
    }
    
    static public void removeAppender(String name)
    {
    	   final LoggerContext context = LoggerContext.getContext(false);
           final Configuration config = context.getConfiguration();
	           
	       Appender appender = config.getAppender(name);
	       appender.stop();
	      if ( config instanceof AbstractConfiguration ) {
	    	
	    	((AbstractConfiguration)config).removeAppender ( name );
	      }
    }
    
//    public void testBuilder() throws Exception {
//        final ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
//        builder.setStatusLevel(Level.TRACE);
//        builder.setConfigurationName("BuilderTest");
//        builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL)
//                .addAttribute("level", Level.TRACE));
//        final AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target",
//                ConsoleAppender.Target.SYSTEM_OUT);
//        appenderBuilder.add(builder.newLayout("PatternLayout").
//                addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
//        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
//                Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
//        builder.add(appenderBuilder);
//
//        LoggerContext ctx = Configurator.initialize(builder.build());
//        logger = ctx.getLogger(LoggingUtil.class.getName());
//        final Configuration config = ctx.getConfiguration();
////        System.out.print(String.format("No configuration", config);
////        assertEquals("Unexpected Configuration", "BuilderTest", config.getName());
////        assertThat(config.getAppenders(), hasSize(equalTo(1)));
//
//    }
//
}
