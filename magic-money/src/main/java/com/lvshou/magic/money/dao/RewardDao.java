package com.lvshou.magic.money.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.money.entity.GroupSelect;
import com.lvshou.magic.money.entity.Reward;

public interface RewardDao extends JpaRepository<Reward, String>{

	public List<Reward> findByTarget(String target);
	
	public List findByTargetPhone(String phone);
	
	@Query(value="select * from reward order by create_time DESC",nativeQuery=true)
	public Page<Reward> findAll(Pageable pageable);
	
	@Query(value="select count(*) from reward",nativeQuery=true)
	public int findRewardAmount();
	/**重新定义的查询*/
	@Query(value="select * from reward where source_name like %?1% order by create_time DESC",nativeQuery=true)
	public Page<Reward> findSourceName(String source,Pageable pageable);
	@Query(value="select count(*) from reward where source_name= ?1",nativeQuery=true)
	public int countSourceName(String source);
	@Query(value="select * from reward where source_phone= ?1 order by create_time DESC",nativeQuery=true)
	public Page<Reward> findSourcePhone(String sourcePhone,Pageable pageable);
	@Query(value="select count(*) from reward where source_phone= ?1",nativeQuery=true)
	public int countSourcePhone(String sourcePhone);
	@Query(value="select * from reward where target_name like %?1% order by create_time DESC",nativeQuery=true)
	public Page<Reward> findTargetName(String targetName,Pageable pageable);
	@Query(value="select count(*) from reward where target_name= ?1",nativeQuery=true)
	public int countTargetName(String targetName);
	@Query(value="select * from reward where target_phone= ?1 order by create_time DESC",nativeQuery=true)
	public Page<Reward> findTargetPhone(String targetPhone,Pageable pageable);
	@Query(value="select count(*) from reward where target_phone= ?1",nativeQuery=true)
	public int countTargetPhone(String targetPhone);
	
	/**按照月份年份统计奖励金额*/
	@Query(value="select sum(total) from reward where target=?3 and YEAR(create_time) =?1 and month(create_time)=?2",nativeQuery=true)
	public BigDecimal countReward(int year,int month,String userId);
	
	@Query(value="select l.monthReward ,b.* from (select t.target as target ,sum(total) as monthReward from reward as t where YEAR(create_time)=?1 and month(create_time)=?2 group by t.target) as l "
			+ " left join (select m.* , u.num_id, u.name , u.phone from money as m left join user as u on m.user_id = u.id) as b on b.user_id=l.target ",nativeQuery=true)
	public List countReward(int year,int month);
	
	@Query(value="select l.monthReward ,b.* from (select t.target as target ,sum(total) as monthReward from reward as t where YEAR(create_time)=?1 and month(create_time)=?2 group by t.target) as l "
			+ " left join (select m.* , u.num_id, u.name , u.phone from money as m left join user as u on m.user_id = u.id) as b on b.user_id=l.target ",nativeQuery=true)
	public List countReward(int year,int month,Pageable pageable);
}
