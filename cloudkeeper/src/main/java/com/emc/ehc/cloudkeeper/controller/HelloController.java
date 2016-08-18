package com.emc.ehc.cloudkeeper.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 18, 2016 9:39:33 AM
* 
*/

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
