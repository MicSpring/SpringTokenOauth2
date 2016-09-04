package com.subha.oauth2.config

import com.subha.oauth2.utils.CustomJWTTokenEnhancer
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
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * Created by user on 8/12/2016.
 */

@Configuration
@EnableAuthorizationServer
class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value('${spring.security.oauth2.resourceid}')
    String RESOURCE_ID// = 'restService'

     //TokenStore tokenStore = new InMemoryTokenStore();
    //JwtTokenStore tokenStore = new JwtTokenStore()

    @Autowired
    //@Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter()
        converter.setSigningKey("123")
        return converter;
    }

    @Bean
    public TokenStore tokenStore(){
        def tokenStore = new JwtTokenStore(accessTokenConverter())
        tokenStore
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        // @formatter:off

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), accessTokenConverter()));


        endpoints
                .tokenStore(tokenStore())
                //.accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(this.authenticationManager)
               // .userDetailsService(userDetailsService);
        // @formatter:on
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        def tokenEnhancer =  new CustomJWTTokenEnhancer()
        tokenEnhancer
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @format  ter:off
        clients
                .inMemory()
                .withClient("clientapp")
                .authorizedGrantTypes("authorization_code")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .resourceIds(RESOURCE_ID)
                .redirectUris("http://anywhere?key=value")
                .secret("123456")
                .and()
                .withClient("clientapp2")
                .authorizedGrantTypes("client_credentials", "password","refresh_token")
                .authorities("ROLE_CLIENT")
                .scopes("read","write")
                .resourceIds(RESOURCE_ID)
                .secret("99999")
                .accessTokenValiditySeconds(92)
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
        tokenServices.setTokenStore(tokenStore())
        //tokenServices.setRefreshTokenValiditySeconds(90)
        tokenServices;
    }
}
