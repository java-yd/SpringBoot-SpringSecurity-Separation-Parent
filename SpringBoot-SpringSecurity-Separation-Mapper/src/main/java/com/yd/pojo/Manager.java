package com.yd.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.annotation.JSONField;

public class Manager  implements Serializable, UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Integer  id;

	private String username;

    private String password;
    
    private String identifying;
    
    private Date gmtCreat;

    private Date gmtModified;

    
    private String  resources;//所有资源url，可能有重复的url，但是没有影响，不做处理
    private boolean isRemoteLogin;//是否异地登录，默认值false

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(resources);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getRemoteLogin() {
		return isRemoteLogin;
	}

	public void setRemoteLogin(boolean isRemoteLogin) {
		this.isRemoteLogin = isRemoteLogin;
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
	
	

	public String getIdentifying() {
		return identifying;
	}

	public void setIdentifying(String identifying) {
		this.identifying = identifying;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", username=" + username + ", password=" + password + ", gmtCreat=" + gmtCreat
				+ ", gmtModified=" + gmtModified + ", resources=" + resources + ", isRemoteLogin=" + isRemoteLogin
				+ "]";
	}

	
	
}