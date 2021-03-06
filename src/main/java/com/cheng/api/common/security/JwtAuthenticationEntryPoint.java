/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import com.cheng.api.common.base.Result;
import com.cheng.api.common.base.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		// This is invoked when user tries to access a secured REST resource without supplying any credentials
		// We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		writer.append(new ObjectMapper().writeValueAsString(Result.fail(ResultCode.INVALID_TOKEN)));
		writer.close();
	}
}
