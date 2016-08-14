package com.subha.oauth2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan

/**
 * Created by user on 8/11/2016.
 */

@SpringBootApplication
@ComponentScan(basePackages = ["org.springframework.boot.autoconfigure.jdbc","com.subha"])
class SpringTokenOauth2Application {

    static main(args) {
        new SpringApplicationBuilder(SpringTokenOauth2Application.class)
                .run(args);

    }
}
