package com.lvshou.magic.money.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.entity.ReturnMoney;

public interface MoneyDao extends JpaRepository<Money, String>{

	public Optional<Money> findById(String id);
	@Query(value="select * from money where user_id=?1",nativeQuery=true)
	public List<Money> findUser(String userId);
	@Query(value="select a.*, b.num_id as numId, b.name as name ,b.phone as phone from money as a left join user as b on a.user_id = b.id order by create_time ASC",nativeQuery=true)
	public List findAllMoneys(Pageable pageable);
	@Query(value="select a.*, b.num_id as numId, b.name as name ,b.phone as phone from money as a left join user as b on a.user_id = b.id order by create_time ASC",nativeQuery=true)
	public List findAllMoneys();
	@Query(value="select count(*) from money",nativeQuery=true)
	public int findMoneyAmount();
	
	@Query(value="select a.*, b.num_id as numId, b.name as name ,b.phone as phone from money as a left join user as b on a.user_id = b.id where a.user_id = ?1",nativeQuery=true)
	public List findUserId(String userId,Pageable pageable);
	@Query(value="select count(*) from money where user_id=?1",nativeQuery=true)
	public int countUserIds(String userId);
	
	/**查找所有*/
	@Query(value="select * from money order by create_time asc",nativeQuery=true)
	public List<Money> moneyNoPage();
}
