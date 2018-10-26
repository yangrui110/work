package com.lvshou.magic.user.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.user.entity.UserHistory;

public interface UserHistoryDao extends JpaRepository<UserHistory,String>{
	
	/**查找所有的*/
	@Query(value="select * from user_history order by create_time",nativeQuery=true)
	public List<UserHistory> findAlls(Pageable pageable);
	@Query(value="select count(*) from user_history ",nativeQuery=true)
	public int countAll();
	
	/**按照月份查找*/
	@Query(value="select * from user_history where month(create_time)=?1 and year(create_time)=?2",nativeQuery=true)
	public List<UserHistory> findMonth(int month,int year,Pageable pageable);
	@Query(value="select count(*) from user_history where month(create_time)=?1 and year(create_time)=?2",nativeQuery=true)
	public int countMonth(int month,int year);
	@Query(value="select * from user_history where month(create_time)=?1 and year(create_time)=?2",nativeQuery=true)
	public List<UserHistory> findMonthNoPage(int month,int year);
	
	/**按照姓名查找*/
	@Query(value="select * from user_history where name=?1",nativeQuery=true)
	public List<UserHistory> findNames(String name,Pageable pageable);
	@Query(value="select count(*) from user_history  where name=?1",nativeQuery=true)
	public int countNames(String name);
	
	/**按照编号查找*/
	@Query(value="select * from user_history where num_id=?1",nativeQuery=true)
	public List<UserHistory> findNumIds(String numId,Pageable pageable);
	@Query(value="select count(*) from user_history  where num_id=?1",nativeQuery=true)
	public int countNumIds(String name);
	
	/**按照手机号查找*/
	@Query(value="select * from user_history where phone=?1",nativeQuery=true)
	public List<UserHistory> findPhones(String phone,Pageable pageable);
	@Query(value="select count(*) from user_history  where phone=?1",nativeQuery=true)
	public int countPhones(String name);
	
}
