package com.lvshou.magic.result;

import com.lvshou.magic.exception.ResultEnum;

public class Result<T> {

	private Integer code;
	private String msg;
	private Integer count;
	private T data;
	
	public Result() {
		
	}
	public Result(Integer code,String message) {
		this.code=code;
		this.msg=message;
	}
	public Result(ResultEnum enum1) {
		this.code=enum1.getCode();
		this.msg=enum1.getMessage();
	}
	public Result(ResultEnum enum1,T data) {
		this.code=enum1.getCode();
		this.msg=enum1.getMessage();
		this.data=data;
	}
	public Result(ResultEnum enum1,T data,Integer count) {
		this.code=enum1.getCode();
		this.msg=enum1.getMessage();
		this.data=data;
		this.count=count;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String message) {
		this.msg = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
