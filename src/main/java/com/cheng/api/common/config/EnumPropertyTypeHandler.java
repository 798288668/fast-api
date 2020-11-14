package com.cheng.api.common.config;

import com.cheng.api.common.base.IPropertyEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举属性值映射数据库数值的handler
 * @author dhy
 * @version 2019/4/23
 */
public class EnumPropertyTypeHandler<E extends IPropertyEnum> extends BaseTypeHandler<E> {

	private Class<E> type;
	private E[] enums;

	public EnumPropertyTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		} else {
			this.type = type;
			this.enums = type.getEnumConstants();
			if (this.enums == null) {
				throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
			}
		}

	}

	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType)
			throws SQLException {
		preparedStatement.setString(i, e.getValue());
	}

	@Override
	public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
		String index = resultSet.getString(s);
		return resultSet.wasNull() ? null : result(index);
	}

	@Override
	public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
		String index = resultSet.getString(i);
		return resultSet.wasNull() ? null : result(index);
	}

	@Override
	public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		String index = callableStatement.getString(i);
		return callableStatement.wasNull() ? null : result(index);
	}

	private E result(String index) {
		try {
			Optional<E> optional = Arrays.stream(enums).filter(item -> index.equals(item.getValue())).findAny();
			return optional.orElse(null);
		} catch (Exception var5) {
			throw new IllegalArgumentException(
					"Cannot convert " + index + " to " + this.type.getSimpleName() + " by value.", var5);
		}
	}
}
