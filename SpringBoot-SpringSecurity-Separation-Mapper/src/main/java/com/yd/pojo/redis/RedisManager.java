package com.yd.pojo.redis;

import java.util.Date;

import lombok.Data;

public class RedisManager {

	private Integer  id;

	private String username;

    private String password;
    
    private String identifying;
    
    private Date gmtCreat;

    private Date gmtModified;

    
    private String  resources;//所有资源url，可能有重复的url，但是没有影响，不做处理
    private boolean isRemoteLogin;//是否异地登录，默认值false
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getGmtCreat() {
		return gmtCreat;
	}
	public void setGmtCreat(Date gmtCreat) {
		this.gmtCreat = gmtCreat;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public boolean getRemoteLogin() {
		return isRemoteLogin;
	}
	public void setRemoteLogin(boolean isRemoteLogin) {
		this.isRemoteLogin = isRemoteLogin;
	}
	public String getIdentifying() {
		return identifying;
	}
	public void setIdentifying(String identifying) {
		this.identifying = identifying;
	}

    
    
}
