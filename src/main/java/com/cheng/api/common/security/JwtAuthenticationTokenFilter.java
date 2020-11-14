/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.base.Result;
import com.cheng.api.common.base.ResultCode;
import com.cheng.api.common.config.RedisUtil;
import com.cheng.api.common.constant.RedisConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.modules.sys.bean.SysUser;
import com.cheng.api.modules.sys.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private UserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(JwtTokenUtil.HEADER);
		if (authHeader != null && authHeader.startsWith(JwtTokenUtil.BEARER) && !request.getServletPath()
				.contains("login")) {
			final String authToken = authHeader.substring(JwtTokenUtil.BEARER.length());
			String username = jwtTokenUtil.getUsernameFromToken(authToken);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				// 处理app登录的并发人数控制
				SysUser user = UserUtils.getUserByLoginName(username);
				String redisToken = RedisUtil.getString(RedisConst.USER_LOGIN_NAME + username);
				if (user.getUserType() == SysEnum.UserType.APP && !Objects.equals(authToken, redisToken)) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					PrintWriter writer = response.getWriter();
					writer.append(
							new ObjectMapper().writeValueAsString(Result.fail(ResultCode.INVALID_HAS_KICKOUT)));
					writer.close();
					return;
				}

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (RedisUtil.exists(RedisConst.USER_LOGIN_TOKEN + authToken) && jwtTokenUtil
						.validateToken(authToken, userDetails)) {

					// 续期
					RedisUtil.setString(RedisConst.USER_LOGIN_TOKEN + authToken, username,
							JwtTokenUtil.REDIS_EXPIRATION);

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);
	}
}
