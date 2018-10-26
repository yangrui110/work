package com.lvshou.magic.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.entity.Message;
import com.lvshou.magic.user.service.MessageService;


@RestController
@RequestMapping("message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@GetMapping("findNews/{userId}")
	public Result findNewMessage(@PathVariable("userId")String uerId){
		List<Message> lists=messageService.findNewMessage(uerId);
		return new Result<>(ResultEnum.OK,lists,lists.size());
		
	}
	
	@DeleteMapping("delete/{id}")
	public Result delete(@PathVariable("id")String id) {
		messageService.delete(id);
		return new Result<>(ResultEnum.OK);
	}
}
