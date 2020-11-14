package com.cheng.api.common.base;

import com.cheng.api.common.util.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author fengcheng
 * @version 2019-07-14
 */
public abstract class BaseAction {

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 * 2016-01-28 17:22:04注释掉，富文本编辑器被转码
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if (text != null) {
					if (Objects.equals(text.trim(), "")) {
						setValue(null);
					} else {
						setValue(StringEscapeUtils.escapeHtml4(text.trim()));
					}
				}
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : null;
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? DateUtils.formatDateTime((Date) value) : "";
			}
		});
	}

}
