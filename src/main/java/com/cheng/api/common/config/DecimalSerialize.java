package com.cheng.api.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author fengcheng
 * @version 2019-07-17
 */
public class DecimalSerialize extends JsonSerializer<BigDecimal> {

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@Override
	public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		if (bigDecimal != null) {
			jsonGenerator.writeString(decimalFormat.format(bigDecimal));
		} else {
			jsonGenerator.writeString("0.00");
		}
	}
}
