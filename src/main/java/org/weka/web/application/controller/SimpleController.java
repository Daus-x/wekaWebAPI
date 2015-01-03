package org.weka.web.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by suad on 1/3/2015.
 */
@RestController
public class SimpleController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "I am working";
    }
}
