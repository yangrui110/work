package com.lvshou.magic.verification.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="sys_role_verification")
public class SysRoleVerification {

	@Id
	private String id;
	private String sys_role_id;
	private String verification_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSys_role_id() {
		return sys_role_id;
	}
	public void setSys_role_id(String sys_role_id) {
		this.sys_role_id = sys_role_id;
	}
	public String getVerification_id() {
		return verification_id;
	}
	public void setVerification_id(String verification_id) {
		this.verification_id = verification_id;
	}

}
