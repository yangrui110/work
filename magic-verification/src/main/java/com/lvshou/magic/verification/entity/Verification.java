package com.lvshou.magic.verification.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="verification")
@DynamicUpdate
public class Verification implements UserDetails{

	  @Id
	  private String id;
	  private String account;
	  private String password;
	  //FetchType.EAGER：急加载。在加载一个实体的时候，其中定义是急加载的的属性(property)和字段(field)会立即从数据库中加载 
      //CascadeType:级联更新
      @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
      @JoinTable(name = "sys_role_verification", joinColumns = @JoinColumn(name = "verification_id", referencedColumnName="id",nullable=false, updatable=false),
    	        inverseJoinColumns = @JoinColumn(name = "sys_role_id",referencedColumnName="id", nullable=false, updatable=false))
      private List<SysRole> roles=new ArrayList<>();
	  private Date createTime;
	  private Date updateTime;


	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }


	  public String getAccount() {
	    return account;
	  }

	  public void setAccount(String account) {
	    this.account = account;
	  }


	  public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }


	  public Date getCreateTime() {
	    return createTime;
	  }

	  public void setCreateTime(Date createTime) {
	    this.createTime = createTime;
	  }


	  public Date getUpdateTime() {
	    return updateTime;
	  }

	  public void setUpdateTime(Date updateTime) {
	    this.updateTime = updateTime;
	  }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//将用户角色作为权限
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<SysRole> roles = this.getRoles();
        for(SysRole role : roles){
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auths;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	}