package com.lvshou.magic.exception;

@SuppressWarnings("serial")
public class CommonException extends RuntimeException{

	private Integer code;
	private ResultEnum enum1;
	public CommonException(ResultEnum enum1) {
		super(enum1.getMessage());
		this.enum1=enum1;
		this.code=enum1.getCode();
	}
	public CommonException(String message) {
		super(message);
		this.code =505;
	}
	public Integer getCode() {
		return code;
	}
	public ResultEnum getEnum1() {
		return enum1;
	}
	
	
}
