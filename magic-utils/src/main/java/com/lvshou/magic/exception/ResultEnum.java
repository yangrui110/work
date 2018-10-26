package com.lvshou.magic.exception;

public enum ResultEnum {

	
	USER_EXIT(10,"用户已存在"),
	ID_NOT_EXIT(11,"待删除id不存在"),
	UPDATE_NOT_EXIT(12,"更新传入不存在"),
	UPDATE_NOT_FIND(13,"未查找到待更新数据"),
	NOT_FIND_CASH_ACCOUNT(14,"未查找到现金账户,需要先绑定账户信息"),
	NOT_FIND_MONEY_TABLE(15,"未查找到个人金额表"),
	CASH_OVER_CONSUME(16,"消费/提现金额超过所有值,多去发展下线吧^_^"),
	USER_NOT_EXIT_REFERRAL(17,"用户不在推荐表中"),
	PARENT_NOT_FIND(18,"查询推荐人出错了~~"),
	CHILD_NONE(19,"当前用户没有推荐他人"),
	REFERRAL_CODE_NOT_EXIT(20,"推荐码不存在"),
	REFERRAL_NUM_ENOUGH(21,"推荐人数已经达到5个人"),
	PHONE_HAS(22,"手机号已经注册"),
	PRODUCT_NOT_ENOUGH(23,"库存量不足"),
	XIAJIA(24,"产品已经下架"),
	NO_REFERRAL(25,"推荐码错误，请确认后填写"),
	OK(0,""),
	LOGIN(300,"未登录，请先登录"),
	LOGIN_IGGAL(301,"登陆不合法"),
	LOGIN_ERROR(400,"账号或者密码不正确"),
	ERROR(500,"内部错误"),
	AUTHEN_ERROR(600,"未登录，请先登录");
	
	private Integer code;
	private String message;
	
	ResultEnum(Integer code,String message) {
		// TODO Auto-generated constructor stub
		this.code=code;
		this.message=message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
