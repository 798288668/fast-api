/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	private final JwtUserDetailsServiceImpl userDetailsService;
	private final JwtTokenUtil jwtTokenUtil;
	private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

	@Autowired
	public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler,
			JwtUserDetailsServiceImpl userDetailsService, JwtTokenUtil jwtTokenUtil,
			JwtLogoutSuccessHandler jwtLogoutSuccessHandler) {
		this.unauthorizedHandler = unauthorizedHandler;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.jwtLogoutSuccessHandler = jwtLogoutSuccessHandler;
	}

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new JwtPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors()
				// we don't need CSRF because our token is invulnerable
				.and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				// don't create session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				// allow anonymous resource requests
				.antMatchers(HttpMethod.GET, "/static/**").permitAll()
				// 对于获取token的rest api要允许匿名访问
				.antMatchers(EXCLUDE_URIS).permitAll()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated().and().logout().logoutUrl("/logout")
				.logoutSuccessHandler(jwtLogoutSuccessHandler);

		// Custom JWT based security filter
		httpSecurity.addFilterBefore(new JwtAuthenticationTokenFilter(userDetailsService(), jwtTokenUtil),
				UsernamePasswordAuthenticationFilter.class);

		// disable page caching
		httpSecurity.headers().cacheControl();
	}

	/**
	 * 允许匿名访问的url
	 */
	private static final String[] EXCLUDE_URIS = {"/login", "/register", "/getCaptcha", "/common", "/uploadImage",
			"/uploadFile", "/sys/contact", "/help/api", "/order/create", "/order/payment", "/order/payment2",
			"/getQrCode", "/order/notify", "/register/submit", "/order/state", "/getSts", "/order/payment/skip",
			"/out/**","/common/**", "/order/qr1", "/order/qr2", "/actuator/**","/"};

}
