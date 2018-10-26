package com.lvshou.magic.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.user.entity.Message;

public interface MessageDao extends JpaRepository<Message, String>{
	
	@Query(value="select * from message where belong_to=?1 and status <> 3 order by create_time ASC",nativeQuery=true)
	public List<Message> findNewMessage(String belongTo);

}
