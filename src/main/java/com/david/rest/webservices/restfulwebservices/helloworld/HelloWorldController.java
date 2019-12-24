package com.david.rest.webservices.restfulwebservices.helloworld;

import com.david.rest.webservices.restfulwebservices.helloworld.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/*
*  use Same package(or Sub Package) of RestfulWebServicesApplication for HelloWorldController
* */
//Controller
@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;

//    @GetMapping(path = "/hello-world-internationalized")
//    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale){
//        return messageSource.getMessage("good.morning.message", null, locale).toString();
//    }


    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(){
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

    // or could use @GetMapping(path = "/hello-world")
    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public String helloWorld(){
        return "hello world";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("hello world");
    }

    //hello-world/path-variable/david
    @RequestMapping(method = RequestMethod.GET, path = "/hello-world/path-variable/{name}")
        public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
            return new HelloWorldBean(String.format("hello world %s", name));
    }

}
