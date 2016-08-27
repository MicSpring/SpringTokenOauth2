package com.subha.oauth2

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 8/14/2016.
 */
@RestController

public class UserController {

    @RequestMapping("/user")
    String getUser(Authentication authentication) {
        println "***** ${authentication.getClass()}"
        "************** I am Foing Great"
    }

    @RequestMapping("/user1")
    String getUser1(Authentication authentication) {
        println "***** ${authentication.getClass()}"
        "************** I do not have authentication"
    }
}