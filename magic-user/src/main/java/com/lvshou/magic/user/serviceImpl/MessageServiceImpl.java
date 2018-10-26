package com.lvshou.magic.user.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvshou.magic.user.dao.MessageDao;
import com.lvshou.magic.user.entity.Message;
import com.lvshou.magic.user.service.MessageService;
import com.lvshou.magic.user.status.MessageStatus;
import com.lvshou.magic.utils.MainUUID;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageDao messageDao;
	
	@Override
	public Message insert(Message message) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(message.getId())) {
			message.setId(MainUUID.getUUID());
		}
		return messageDao.save(message);
	}


	@Override
	public List<Message> findNewMessage(String belongTo) {
		// TODO Auto-generated method stub
		return messageDao.findNewMessage(belongTo);
	}


	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		Message msg=messageDao.findById(id).orElse(null);
		if(msg==null) {return;}
		msg.setStatus(MessageStatus.DELETE);
		messageDao.save(msg);
	}

}
