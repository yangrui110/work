package com.lvshou.magic.operation.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.operation.entity.Operation;

public interface OperationDao extends JpaRepository<Operation, String>{
	
	@Query(value="select * from operation where user_id=?1 order by create_time DESC",nativeQuery=true)
	public List<Operation> findAllByUserId(String userId);
	
	public Page<Operation> findByOperateAndStatus(String operate,int status,Pageable pageable);
	public List<Operation> findByStatus(int status);
	@Query(value="select count(*) from operation where status=?1",nativeQuery=true)
	public int findOperationStatusAmount(int status);
	
	/**按月份查找*/
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where a.status=?1 and year(a.create_time)=?2 and month(a.create_time)=?3 order by a.create_time",nativeQuery=true)
	public List findMonth(int status,int year,int month,Pageable pageable);
	@Query(value="select count(*) from operation as a left join user as b on a.user_id=b.id where a.status=?1 and year(a.create_time)=?2 and month(a.create_time)=?3 order by a.create_time",nativeQuery=true)
	public int countMonth(int status,int year,int month);
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where a.status=?1 and year(a.create_time)=?2 and month(a.create_time)=?3 order by a.create_time",nativeQuery=true)
	public List findMonthNoPage(int status,int year,int month);
	
	/**按照用户名查找*/
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where b.name=?1 and a.status=?2 order by a.create_time",nativeQuery=true)
	public List findNames(String name,int status,Pageable pageable);
	@Query(value="select count(*) from operation as a left join user as b on a.user_id=b.id where b.name=?1 and a.status=?2 order by a.create_time",nativeQuery=true)
	public int countNames(String name,int status);
	/**按照编号查找*/
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where b.num_id=?1 and a.status=?2 order by a.create_time",nativeQuery=true)
	public List findNumId(String numId,int status,Pageable pageable);
	@Query(value="select count(*) from operation as a left join user as b on a.user_id=b.id where b.num_id=?1 and a.status=?2 order by a.create_time",nativeQuery=true)
	public int countNumId(String numId,int status);
	/**按照手机号查找*/
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where b.phone=?1 and a.status=?2 order by a.create_time",nativeQuery=true)
	public List findPhone(String phone,int status,Pageable pageable);
	@Query(value="select count(*) from operation as a left join user as b on a.user_id=b.id where b.phone=?1 and a.status=?2 order by a.create_time",nativeQuery=true)
	public int countPhone(String phone,int status);
	/**将会查找所有的值*/
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where  a.status=?1 order by a.create_time",nativeQuery=true)
	public List finds(int status,Pageable pageable);
	@Query(value="select count(*) from operation as a left join user as b on a.user_id=b.id where  a.status=?1 order by a.create_time",nativeQuery=true)
	public int counts(int status);
	@Query(value="select a.*,b.num_id,b.name,b.phone from operation as a left join user as b on a.user_id=b.id where  a.status=?1 order by a.create_time",nativeQuery=true)
	public List findsNoPage(int status);
}
