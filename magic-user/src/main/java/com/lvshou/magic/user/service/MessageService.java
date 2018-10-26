package com.lvshou.magic.user.service;

import java.util.List;

import com.lvshou.magic.user.entity.Message;

public interface MessageService {

	public Message insert(Message message);
	
	public void delete(String id);
	
	public List<Message> findNewMessage(String belongTo);
}
