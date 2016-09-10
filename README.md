Learning <b>OAuth2</b> security protocol has always been one of my prime objectives. After integrating JWT token with Spring Security, I thought of giving a try with learning OAuth2 with Spring Security, as Spring Security provides implementation for OAuth2. <br/>

To know the basics of OAuth2 I followed <b><a href="https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2">this.</a></b> This article along with <b><a href="https://aaronparecki.com/2012/07/29/2/oauth2-simplified">Aaron Parecki's blog</a></b> really helped me in getting the concept behind this security protocol.<br/>

Let's revise the components involved here:<br/><br/>

<b>

•Resource Owner <br/>
•Client <br/>
•Resource Server <br/>
•Authorization Server <br/>


</b>

and the grant types are:<br/>

<b>

•Authorization Code <br/>
•Implicit <br/>
•Resource Owner Password Credentials <br/>
•Client Credentials <br/>


</b>

We have implemented both the Resource and Authorization server at the same endpoint for the sake of simplicity, however we can implement them separately too.<br/>
And out of the four grant types I have been successful in experimenting with the last two only. I will try to share those experience here.<br/>
I have selected JWT as the underlying token generation standard.<br/>

The <b><a href="https://github.com/MicSpring/SpringTokenOauth2">Codebase</a></b> is available in <b>GITHUB</b>.<br/> <br/>

Spring with <b>@EnableAuthorizationServer</b> annotation  and <b>AuthorizationServerConfigurerAdapter</b> class helps in implementing OAuth2 AuthrizationServer.<br/>
And for ResourceServer there is <b>@EnableResourceServer</b> annotation and <b>ResourceServerConfigurerAdapter</b> class.<br/>

Accessing every resource requires a  mandatory valid JWT Token to be present in the request.
Every request is being validated for an autheticated token, if the token is valid then only the resource can be accessed.
If a token validity ends, then we can refresh the access token with refresh token, that is obtained at the very first time when the access token in requested.<br/>
Now if the refresh token validity ends we need to fetch the access token again.<br/>

All these token generation and refresh tasks are being handled by Authorization server and validation of the tokens (access and refresh both), while request is made for particular resource is being handled by Resource server.<br/>

Let's describe this with an example:<br/>

At the very beginning, the access token can be accessed at:<br/>

For <b>Resource Owner Password Credentials</b> grant type:<br/>

http://localhost:8080/SpringTokenOauth2/oauth/token?grant_type=password&username=BOND&password=BOND007&client_id=clientapp2&client_secret=99999 <br/>


and in the request header there should be: <br/>


<b>Authorization  Bearer Y2xpZW50YXBwMjo5OTk5OQ==</b><br/>

<b>Y2xpZW50YXBwMjo5OTk5OQ==<b> is <b>clientapp2:99999</b><br/>

And <b>Client Credentials</b> grant type:<br/>

http://localhost:8080/SpringTokenOauth2/oauth/token?grant_type=client_credentials&client_id=clientapp2&client_secret=99999<br/>


For Client Credentials grant type nothing required to be present in the request header.<br/>

The output in the above case would be:<br/>

<b>

{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0NzMwMTQwOTcsInVzZXJfbmFtZSI6IkJPTkQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwibWljIjoib2siLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwianRpIjoiZWVmOTBkZDctZmY4YS00NzNiLTg0NDItYTcyN2U4OGFlZGRiIiwiY2xpZW50X2lkIjoiY2xpZW50YXBwMiJ9.BNxsjqpdXnlDNeXgZZtOvJxawrwGW4yHcizqxkgL4CQ",
  "token_type": "bearer",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0NzU2MDYwMDUsInVzZXJfbmFtZSI6IkJPTkQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwibWljIjoib2siLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwianRpIjoiZGE3OTk2NjMtZTM1Mi00NTFjLTgyYzEtYTBjZTQ5MDA4ZjVlIiwiY2xpZW50X2lkIjoiY2xpZW50YXBwMiIsImF0aSI6ImVlZjkwZGQ3LWZmOGEtNDczYi04NDQyLWE3MjdlODhhZWRkYiJ9.FIw0ICyvR2ZibLylyE1huQGMahkWO_W4l8a-QeOISBw",
  "expires_in": 91,
  "scope": "read write",
  "mic": "ok",
  "jti": "eef90dd7-ff8a-473b-8442-a727e88aeddb"
}


</b>



Now with the access token as present above we can access resource as: <br/>



http://localhost:8080/SpringTokenOauth2/resources/user <br/>



and in the request header we have:<br/>

<b>
Authorization bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0NzMwMTQwOTcsInVzZXJfbmFtZSI6IkJPTkQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwibWljIjoib2siLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwianRpIjoiZWVmOTBkZDctZmY4YS00NzNiLTg0NDItYTcyN2U4OGFlZGRiIiwiY2xpZW50X2lkIjoiY2xpZW50YXBwMiJ9.BNxsjqpdXnlDNeXgZZtOvJxawrwGW4yHcizqxkgL4CQ
</b>

i.e. 

Authorization bearer <<ACCESS_TOKEN>>

Now when the access token expires, here we have configured the expiry time as 90 seconds (Can be referred from Authorizationserver config), then we no longer can access resource with this access token, we need to use the refresh token to refresh the access token as:<br/>



http://localhost:8080/SpringTokenOauth2/oauth/token?grant_type=refresh_token&refresh_token=<<refresh_token as present above>>&client_id=clientapp2&client_secret=99999<br/>


This endpoint will generate an output like above just in <b>Access Token</b> field we will be getting a new Access Token, the refresh token will remain same.<br/><br/>

Now if the refresh token expires, then we need to make the request at the endpoint <b>/oauth/token</b> just like we did at the very beginning.
This is very much like fresh login.<br/><br/>

Let's explore the AuthorizationServer configuration in detail: <br/><br/>

The AuthorizationServer configuration server extends the adapter <b>AuthorizationServerConfigurerAdapter</b>.<br/>
This adapter class with the help of <b>ClientDetailsServiceConfigurer</b> helps in embedding client details <b></b>.<br/><br/>

For example:

<pre>

				clients<br/>
                .inMemory()<br/>
                .withClient("clientapp")<br/>
                .authorizedGrantTypes("authorization_code")<br/>
                .authorities("ROLE_CLIENT")<br/>
                .scopes("read", "write")<br/>
                .resourceIds(RESOURCE_ID)<br/>
                .redirectUris("http://anywhere?key=value")<br/>
                .secret("123456")<br/>
                .and()<br/>
                .withClient("clientapp2")<br/>
                .authorizedGrantTypes("client_credentials", "password","refresh_token")
                .authorities("ROLE_CLIENT")<br/>
                .scopes("read","write")<br/>
                .resourceIds(RESOURCE_ID)<br/>
                .secret("99999")<br/>
                .accessTokenValiditySeconds(92)<br/>


</pre>

The Client id, secret, scopes, grant types etc. can all be configured with Fluent API. Here we have configured two clients <b>1. clientapp and 2. clientapp2</b><br/><br/>
In our example only references of <b>clientapp2</b> as for this client only we have configured <b>"client_credentials", "password","refresh_token"</b>
grant types.<br/><br/>

The <b>AuthorizationServerEndpointsConfigurer</b> does all token related configuration along with Authentication Manager (and UserDetailsService if necessary)<br/><br/>

The underlying token implementation that we have used here is JWT, that is the reason we have used <b>JwtTokenStore</b> and set it in the <b>DefaultTokenServices</b>. The <b>JwtAccessTokenConverter</b> does all the signing work which is required for any JwtToken. <br/>
The same converter conversion needs to be present in the Resource Server endpoint so that proper conversion JWT Token can take place which is required for Token validation while accessing the resource.<br/><br/>

Other token implementation can also be provided with <b>InMemoryTokenStore</b> etc.<br/><br/>

Along with this we have also used Custom Token enhancer, to augment JWT token with some additional information.<br/>
For other security related configuration at the Authorization Server end we can use <b>AuthorizationServerSecurityConfigurer</b>, though we have 
not used here.<br/><br/>

Now at the resource server endpoint there is not too much of configuration except for Token store implementation in <b>ResourceServerSecurityConfigurer</b>, which is required for Token validation while accessing resource.<br/><br/>

There is also a class named <b>SecurityConfig</b> which is engaged in setting normal Spring Security configuration.<br/>

This is in short about Oauth2 implementation with Spring Security.<br/><br/>
<b>Please share your thoughts and feedback.</b>