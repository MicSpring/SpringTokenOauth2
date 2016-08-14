package com.subha.oauth2

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 8/14/2016.
 */
@RestController

public class UserController {

    @RequestMapping("/user")
    String getUser() {
        "************** I am Foing Great"
    }
}