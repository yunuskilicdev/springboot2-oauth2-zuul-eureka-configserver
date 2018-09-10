package com.kilic.yunus.uaaserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    public static final String SERVICE = "service";
    public static final String TRUST = "trust";
    public static final String WEB = "web";
    public static final String MOBILE = "mobile";
    public static final String READ = "read";
    public static final String WRITE = "write";

    public static final String hasServiceScope = "#oauth2.hasScope('" + SERVICE + "') ";
    public static final String hasReadScope = "#oauth2.hasScope('" + READ + "') ";
    public static final String hasWriteScope = "#oauth2.hasScope('" + WRITE + "') ";
    public static final String hasWebScope = "#oauth2.hasScope('" + WEB + "') ";
    public static final String hasMobileScope = "#oauth2.hasScope('" + MOBILE + "') ";
    public static final String hasServiceScopeAndWebScope = hasServiceScope + "OR #oauth2.hasScope('" + WEB + "')";
    public static final String hasServiceScopeAndMobileScope = hasServiceScope + "OR #oauth2.hasScope('" + MOBILE + "')";
    public static final String hasServiceScopeAndMobileScopeAndWebScope = hasServiceScopeAndWebScope + "OR #oauth2.hasScope('" + MOBILE + "')";
    public static final String hasMobileScopeAndWebScope = hasMobileScope + "OR " + hasWebScope;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    AccessTokenConverter accessTokenConverter;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory().withClient("service").secret("{noop}service")
                .authorizedGrantTypes("client_credentials", "authorization_code", "password", "refresh_token")
                .scopes(SERVICE, READ, WRITE, TRUST)
                .autoApprove(true)
                .authorities("ROLE_TRUSTED_CLIENT")
                .accessTokenValiditySeconds(7200)
                .and()
                .withClient("web_client").secret("{noop}web_clnt")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes(WEB, READ, WRITE)
                .accessTokenValiditySeconds(7200) // 1 hour
                .refreshTokenValiditySeconds(14400) // 2 hours
                .and()
                .withClient("mobile_client").secret("{noop}mobile_clnt")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes(MOBILE, READ, WRITE)
                .accessTokenValiditySeconds(7200) // 1 hour
                .refreshTokenValiditySeconds(14400); // 2 hours
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter);
    }
}
