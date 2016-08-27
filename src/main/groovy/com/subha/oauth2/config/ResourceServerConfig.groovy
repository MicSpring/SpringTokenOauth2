package com.subha.oauth2.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

/**
 * Created by user on 8/14/2016.
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig extends ResourceServerConfigurerAdapter  {

    @Value('${spring.security.oauth2.resourceid}')
    String RESOURCE_ID

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().authenticated()

    }

}
