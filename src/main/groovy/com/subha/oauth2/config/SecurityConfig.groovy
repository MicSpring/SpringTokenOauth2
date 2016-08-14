package com.subha.oauth2.config

import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Created by user on 8/7/2016.
 */

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint


   /* @Autowired
    private AuthenticationManagerBuilder $authManagerBuilder*/

    /**
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     *
     * This is another way autowiring an instance and configuring it at the same time
     *
     */

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        println "***********  The Authentication Manager Builder is: ${authenticationManagerBuilder.getClass()}"
       //println "*********** AuthManagerBuilder: $authManagerBuilder"

        authenticationManagerBuilder
                .userDetailsService userDetailsService
                /*.passwordEncoder(new BCryptPasswordEncoder());*/


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        def authenticationManager = super.authenticationManagerBean()
        authenticationManager
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
    }

}
