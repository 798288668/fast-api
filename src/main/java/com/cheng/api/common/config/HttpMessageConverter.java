package com.cheng.api.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.math.BigDecimal;

/**
 * @author fengcheng
 * @version 2018/6/19.
 */
@Slf4j
@Configuration
public class HttpMessageConverter {

	/**
	 * jackson2HttpMessageConverter覆盖某些配置
	 */
	@Autowired
	public void jackson2HttpMessageConverter(MappingJackson2HttpMessageConverter jackson2HttpMessageConverter) {
		ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
		// long，Long序列化为String
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(BigDecimal.class, new DecimalSerialize());
		objectMapper.registerModule(simpleModule);
	}

}
