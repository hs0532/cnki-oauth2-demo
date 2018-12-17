package com.cnki.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.cnki.demo.service.BaseUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	protected UserDetailsService userDetailsService() {// 认证数据库用户
		return new BaseUserDetailService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable();
		http.requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**")
		.and().authorizeRequests()
				.antMatchers("/oauth/**")
				.authenticated()
				.and()
				.formLogin().loginPage("/login").failureUrl("/login?error").permitAll();
	}

	/**
	 * 需要配置这个支持password模式
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
