/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.base;

/**
 * 返回结果常量
 *
 * @author fengcheng
 * @version 2017/3/14
 */
public class ResultCode {
	private ResultCode() {
	}

	public static final String SUCCESS_CODE = "0";
	public static final String FAILD_CODE = "1";
	public static final String SUCCESS = "处理成功";
	public static final String ERROR_UNKNOWN = "处理失败，请稍后再试或联系管理员";
	public static final String FAILD_PARAM = "提交数据不规范";
	public static final String FAILD_MAX_UPLOAD_SIZE = "上传文件不能超过2M";
	public static final String FAILD_PERMISSION = "没有权限";
	public static final String REQUEST_REPEAT = "请勿重复请求";
	public static final String REQUEST_LIMIT = "请求过于频繁";
	public static final String INVALID_TOKEN = "未登录或登录过期";
	public static final String INVALID_DIGEST = "签名校验失败";
	public static final String INVALID_NAME_OR_PASS = "登录名或密码错误";
	public static final String INVALID_LOGIN_NAME_EXIST = "登录名已存在！";
	public static final String INVALID_USER_NAME_EXIST = "姓名已存在！";
	public static final String INVALID_PASS = "密码错误";
	public static final String INVALID_HAS_KICKOUT = "该账号已在其他设备登陆";
	public static final String ERROR_PAY_PASS = "支付密码错误";
	public static final String ERROR_OPERATE_CURRENT = "当前用户不能操作，请联系管理员";
	public static final String ERROR_STATUS = "当前状态不能进行此操作";
	public static final String INVALID_DATA = "无效的数据";
	public static final String INVALID_NAME_FREEZE = "账号被冻结，请联系管理员！";
	public static final String INVALID_NAME_AUDIT = "账号审核中，请等待或联系管理员！";

	public static final String ERROR_DEL_ADMIN = "该账号不能删除！";
	public static final String ERROR_SET_CHANNEL_RATE = "通道费率设置错误，请联系管理员！";
	public static final String ERROR_PARAM = "提交信息有误，请仔细核对！";
	public static final String INVALID_CAPTCHA = "无效的验证码或验证码过期";
	public static final String ERROR_ORDER_EXIST = "该订单已存在";
	public static final String ERROR_ORDER_STATUS = "该订单已被处理";
	public static final String ERROR_MONEY = "金额错误，请仔细核对";
	public static final String ERROR_WITHDRAW = "余额不足";
	public static final String ERROR_WITHDRAW_HAS = "您已提交过提现申请，请等待客服处理";
	public static final String ERROR_IN_DEPOSIT_HAS = "您已提交过充值申请，请等待客服处理";
	public static final String ERROR_DEPOSIT_MONEY = "保证金不足，请先缴纳保证金";
	public static final String ERROR_CREATE_ORDER_WITHOUT_CODE = "未匹配到合适的支付码，请联系客服";
	public static final String ERROR_CODE_EXIST = "账户昵称已存在";
	public static final String ERROR_BANK_ACCOUNT_EXIST = "银行账户已存在";
	public static final String ERROR_CODE_DELETE = "该挂码已开启，不能删除";
	public static final String ERROR_OPERATE = "操作非法";
	public static final String ERROR_WITHOUT_BANK = "请先添加默认的银行卡信息！";
	public static final String ERROR_WITHOUT_SYS_BANK = "系统暂未提供银行卡信息，请先联系客服！";
	public static final String ERROR_OUT_WITHDRAW = "该下放订单号已存在";
	public static final String ERROR_DEPOSIT_RESET = "账目不平衡，请先进行保证金平账";

	public static final String ERROR_RULE_DAY_IN_TIMES = "每日充值次数超过上限 ";
	public static final String ERROR_RULE_DAY_MAX_IN_MONEY = "每日充值金额超过上限 ";
	public static final String ERROR_RULE_ONCE_MAX_IN_MONEY = "单笔充值金额超过上限 ";
	public static final String ERROR_RULE_ONCE_MIN_IN_MONEY = "单笔充值金额低于最小值 ";
	public static final String ERROR_RULE_DAY_OUT_TIMES = "每日提现次数超过上限 ";
	public static final String ERROR_RULE_DAY_MAX_OUT_MONEY = "每日提现金额超过上限 ";
	public static final String ERROR_RULE_ONCE_MAX_OUT_MONEY = "单笔提现金额超过上限 ";
	public static final String ERROR_RULE_ONCE_MIN_OUT_MONEY = "单笔提现金额低于最小值 ";

}
