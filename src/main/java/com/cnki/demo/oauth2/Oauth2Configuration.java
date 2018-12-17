package com.cnki.demo.oauth2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.cnki.demo.service.MyRedisTokenStore;

@Configuration
public class Oauth2Configuration extends ResourceServerConfigurerAdapter {


	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


		@Override
		public void configure(HttpSecurity http) throws Exception {
		
			http.requestMatchers().antMatchers("/api/**")
            .and()      
            .authorizeRequests()
            .antMatchers("/api/**").authenticated();
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

		@Autowired
		AuthenticationManager authenticationManager;
		@Autowired
		RedisConnectionFactory redisConnectionFactory;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			
			clients.inMemory().withClient("demo")
            .secret("{noop}demoApp")
            .redirectUris("http://baidu.com")//code授权添加
            .authorizedGrantTypes("authorization_code","client_credentials", "password", "refresh_token")
            .scopes("all")
            .resourceIds("oauth2-resource")
            .accessTokenValiditySeconds(1200)
            .refreshTokenValiditySeconds(50000);

		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore( new MyRedisTokenStore(redisConnectionFactory) ) //  Redis 存储oauth 的token和一些令牌信息
					// .tokenStore(new RedisTokenStore(redisConnectionFactory))
					.authenticationManager(authenticationManager).allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.realm("oauth2-resources") // code授权添加
					.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()") // allow
																							// check
																							// token
					.allowFormAuthenticationForClients();
		}

	}
}
