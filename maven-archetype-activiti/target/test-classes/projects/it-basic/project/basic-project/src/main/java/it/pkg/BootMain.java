package it.pkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
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
@Controller
//@ImportResource("classpath:spring-config.xml")
@EnableAutoConfiguration
public class BootMain
{

    @RequestMapping(value="/start", method= RequestMethod.POST)
    @ResponseBody
    void start_workflow(@RequestBody String json ) {

        System.out.println("Hello World");
    }

    public static void main( String[] args )
    {
        SpringApplication.run(BootMain.class, args);
    }
}
