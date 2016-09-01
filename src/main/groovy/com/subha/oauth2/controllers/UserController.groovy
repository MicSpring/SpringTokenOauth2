package com.subha.oauth2

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 8/14/2016.
 */
@RestController
@RequestMapping("/resources")
public class UserController {

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    String getUser(Authentication authentication) {
       // println "***** ${authentication.getClass()}"
        "***** ${authentication.getClass()} \n\n ************** I am Foing Great"
    }

    @RequestMapping(value = "/user1",method = RequestMethod.POST)
    String getUser1(Authentication authentication) {
       // println "***** ${authentication.getClass()}"
        "***** ${authentication.getClass()} \n" +
"************** I do not have authentication"
    }
}