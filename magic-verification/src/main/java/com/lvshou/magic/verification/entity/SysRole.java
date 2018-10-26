package com.lvshou.magic.verification.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="sys_role")
public class SysRole {

    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}