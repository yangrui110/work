package com.lvshou.magic.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lvshou.magic.result.Result;

@ControllerAdvice
public class ExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(CommonException.class)
	public Result handler(CommonException exception) {
		return new Result(exception.getCode(), exception.getMessage());
	}
}
