/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.config;

import com.cheng.api.common.base.BaseException;
import com.cheng.api.common.base.Result;
import com.cheng.api.common.base.ResultCode;
import com.cheng.api.common.base.UnauthorizedException;
import com.cheng.api.common.security.RequestLimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengcheng
 * @version 2017/4/7
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	@ExceptionHandler
	public Result<Object> exceptionHandler(Throwable e) {
		log.error("error: {0}", e);
		return Result.fail();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BaseException.class)
	public Object baseException(BaseException e) {
		String message = e.getMessage();
		log.error(message);
		if (message.contains("BaseException:")) {
			message = message.substring(message.indexOf("BaseException:") + 14);
		}
		return Result.fail(message);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
	public Object methodArgumentNotValidException(Exception e) {
		Result<Object> result = Result.fail(ResultCode.FAILD_PARAM);
		List<FieldError> fieldErrors;
		List<String> error = new ArrayList<>();
		if (e instanceof BindException) {
			fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
			fieldErrors.forEach(x -> error.add("\"" + x.getField() + "\"" + x.getDefaultMessage()));
		} else if (e instanceof MethodArgumentNotValidException) {
			fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
			fieldErrors.forEach(x -> error.add("\"" + x.getField() + "\"" + x.getDefaultMessage()));
		} else if (e instanceof ConstraintViolationException) {
			((ConstraintViolationException) e).getConstraintViolations()
					.forEach(x -> error.add("\"" + x.getPropertyPath() + "\"" + x.getMessage()));
		}
		result.setMessage(ResultCode.FAILD_PARAM + "：" + String.join("，", error));
		return result;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(UsernameNotFoundException.class)
	public Object usernameNotFoundException(UsernameNotFoundException e) {
		return Result.fail(ResultCode.INVALID_NAME_OR_PASS);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Object maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		log.error(e.getMessage());
		return Result.fail(ResultCode.FAILD_MAX_UPLOAD_SIZE);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Object httpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error(e.getMessage());
		return Result.fail(ResultCode.FAILD_PARAM);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Object httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		log.error(e.getMessage());
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<Object> methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ClientAbortException.class)
	public Object clientAbortException(ClientAbortException e) {
		log.error(e.getMessage());
		return null;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(RequestLimitException.class)
	public Result<Object> requestLimitException(RequestLimitException e) {
		log.error(e.getMessage());
		return Result.fail(ResultCode.REQUEST_LIMIT);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public Result<Object> unauthorizedException(UnauthorizedException e) {
		return Result.fail(e.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public Result<Object> accessDeniedException(AccessDeniedException e) {
		return Result.fail(ResultCode.FAILD_PERMISSION);
	}
}
