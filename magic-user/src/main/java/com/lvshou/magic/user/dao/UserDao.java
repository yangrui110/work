package com.lvshou.magic.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.From;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvshou.magic.user.entity.User;

public interface UserDao extends JpaRepository<User, String>{

	public Optional<User> findById(String id);
	public List<User> findByName(String name);
	public List<User> findByPhone(String phone);
	public List<User> findByReferralCode(String code);
	@Query(value="select * from user where parent_code=?1 order by create_time ASC",nativeQuery=true)
	public List<User> findByParentCode(String code);
	public List<User> findByNumId(String numId);
	@Query(value="select * from user order by create_time ASC",nativeQuery=true)
	public Page<User> findAll(Pageable pageable);
	public Page<User> findAllByPhone(String phone,Pageable pageable);
	@Query(value="select * from user where vip =?1 order by create_time ASC",nativeQuery =true)
	public Page<User> findAllByVip(int vip,Pageable pageable);
	public Page<User> findAllByName(String name,Pageable pageable);
	@Query(value="select id from User where createTime>?1")
	public List findTimes(Date date);
	@Query(value="select count(*) from user where vip=?1",nativeQuery=true)
	public int findByVipAmount(int vip);
	@Query(value="select count(*) from user",nativeQuery=true)
	public int findAllUsers();
	
	public Page<User> findAllByNumId(String numId,Pageable pageable);
	public Page<User> findAllByReferralCode(String referralCode,Pageable pageable);
	@Query(value="select * from user where name like %?1% order by create_time",nativeQuery=true)
	public Page<User> findName(String name,Pageable pageable);
	@Query(value="select count(*) from user where name like %?1%",nativeQuery=true)
	public int countsName(String name);
	/**根据各种信息查找VIP*/
	public Page<User> findAllByNumIdAndVip(String numId,int vip,Pageable pageable);
	@Query(value="select * from user where name like %?1% and vip=?2",nativeQuery=true)
	public Page<User> findVipName(String name,int vip,Pageable pageable);
	public Page<User> findAllByphoneAndVip(String phone,int vip,Pageable pageable);
	public Page<User> findAllByReferralCodeAndVip(String referralCode,int vip,Pageable pageable);
	/**查找name相似的数量*/
	@Query(value="select count(*) from user where name like %?1% and vip=?2",nativeQuery=true)
	public int countVipName(String name,int vip);
	
	public List<User> findByNameAndProvince(String name,String province);
	
	public User findByWid(String wid);
	
	@Query(value="select * from user where name=?1 and phone=?2 order by create_time",nativeQuery=true)
	public List<User> findByNameAndPhone(String userName,String phone);
	
	@Query(value="select * from user where name=?1 and referral_code=?2",nativeQuery=true)
	public List<User> findByNameAndPhoneAndReferralCode(String name,String referralCode);
	
	@Query(value="select * from user where vip<> ?1 and vip <> ?2 and vip<> ?3",nativeQuery=true)
	public List<User> vipNotPlainAndPre(int plain,int pre,int special);
	
	@Query(value="select count(*) from user where vip =?1",nativeQuery=true)
	public int countPartners(int partner);
	@Query(value="select * from user where vip = ?1",nativeQuery=true)
	public List<User> pagePartners(int director,Pageable pageable);
	
	@Query(value="select count(*) from user where vip =?1",nativeQuery=true)
	public int countDirectors(int partner);
	@Query(value="select * from user where vip = ?1",nativeQuery=true)
	public List<User> pageDirectors(int director,Pageable pageable);
	
	public List<User> findByDirectPush(String directPush);
	
	@Query(value="select * from user order by create_time",nativeQuery=true)
	public List<User> findAllUsersNoPage();
	
	@Query(value="select * from user where vip=?1",nativeQuery=true)
	public List<User> findAllByVips(int vip);
	@Query(value="select * from user where vip=?1 order by create_time",nativeQuery=true)
	public List<User> findAllByVips(int vip,Pageable pageable);
	
	@Query(value="select * from user where ify=1 order by create_time",nativeQuery=true)
	public List<User> allNoIfy(Pageable pageable);
	@Query(value="select * from user where ify=1 order by create_time",nativeQuery=true)
	public List<User> allNoIfyNoPage();
	@Query(value="select count(*) from user where ify=1",nativeQuery=true)
	public int amountNoIfy();
	
	/**查找下月的总推荐人数目*/
	@Query(value="select * from user where parent_code=?3 and year(vip_time)=?1 and month(vip_time)=?2",nativeQuery=true)
	public List<User> nextMonthAmount(int year,int month,String parentCode);
		
	}
