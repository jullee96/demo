package com.example.demo.sample;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
@EnableAutoConfiguration
public class SampleController {
 
    @RequestMapping(value="/")
    public String sampleHome() {
        System.out.println("Hello Spring Boot!");
        return "/sample/view";
        
    }
}