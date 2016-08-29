package com.subha.oauth2.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * Created by user on 8/12/2016.
 */

@Configuration
@EnableAuthorizationServer
class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value('${spring.security.oauth2.resourceid}')
    String RESOURCE_ID// = 'restService'

     TokenStore tokenStore = new InMemoryTokenStore();
    //JwtTokenStore tokenStore = new JwtTokenStore()

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        // @formatter:off
        endpoints
                .tokenStore(this.tokenStore)
                .authenticationManager(this.authenticationManager)
                //.userDetailsService(userDetailsService);
        // @formatter:on
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @format  ter:off
        clients
                .inMemory()
                .withClient("clientapp")
                .authorizedGrantTypes("password", "refresh_token", "client_credentials","authorization_code")
                .authorities("USER")
                .scopes("read", "write")
                .resourceIds(RESOURCE_ID)
                .secret("123456")
                .accessTokenValiditySeconds(30)
                .refreshTokenValiditySeconds 60
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients()
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true)
        tokenServices.setTokenStore(this.tokenStore)
        tokenServices;
    }
}
