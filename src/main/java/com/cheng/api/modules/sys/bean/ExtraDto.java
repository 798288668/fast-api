package com.cheng.api.modules.sys.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author fengcheng
 * @version 2019-07-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String qrUrl;
	private BigDecimal totalMoney;
	private BigDecimal totalMoneyDone;
	private BigDecimal totalMoneyRate;
	private Long totalCount;
	private Long totalCountDone;
	private BigDecimal totalCountRate;
}
