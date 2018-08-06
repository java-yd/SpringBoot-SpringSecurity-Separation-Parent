package com.yd.config.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.alibaba.druid.support.json.JSONUtils;
import com.yd.config.exception.CommonException;
import com.yd.constant.ConstantCode;
import com.yd.constant.StatusCodeEnum;
import com.yd.pojo.redis.RedisManager;
import com.yd.service.RedisService;
import com.yd.util.JsonUtils;

/**
 * 返回权限验证的接口
 * 
 *
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

	@Autowired
	private  RedisService  redisService;
	

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		boolean hasPermission = false;
		
//		hasPermissionBySecurityContextHolder(request,authentication,hasPermission);
		return hasPermissionByRedis(request,hasPermission);
		
	}

	

	//基于redis做权限判断
	private boolean hasPermissionByRedis(HttpServletRequest request,boolean hasPermission) {
		
		String token = request.getHeader(ConstantCode.WEB_TOKEN);
		
		//token参数错误
		if(StringUtils.isBlank(token)){
			//设置错误码
			request.setAttribute(ConstantCode.RBACSERVICE_REQUEST_ATTRIBUTE_KEY,StatusCodeEnum.INVALID_TOKEN.getCode());
			return  hasPermission;
		}
		
		String redisManagerJson = redisService.get(token);
		
		//已登录
		if(StringUtils.isNotBlank(redisManagerJson)){
			RedisManager manager = JsonUtils.jsonToPojo(redisManagerJson, RedisManager.class);
			
			//是否异地登录
			boolean remoteLogin = manager.getRemoteLogin();
			if(remoteLogin){
				//设置错误码
				request.setAttribute(ConstantCode.RBACSERVICE_REQUEST_ATTRIBUTE_KEY,StatusCodeEnum.MANAGER_REMOTE_LOGIN.getCode());
				return  hasPermission;
			}
			
			if (manager.getId().equals(0)) {//超级管理员，直接放行
				hasPermission = true;
				return  hasPermission;
			}
			
			//判断权限，假如用户随便输一个url，本来是要返回404的，但是这里会返回权限不足，如果要返回404，需要得到数据库所有url，然后和当前用户请求url匹配，不存在就返回404
			String resources = manager.getResources();
			if(StringUtils.isNotBlank(resources)){
				List<String> resourceList = Arrays.asList(resources.split(","));
				String  urlRecord = request.getRequestURI();//请求url
				// 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
				for (String url : resourceList) {
					if (antPathMatcher.match(url, urlRecord)) {
						hasPermission = true;
						break;
					}
				}	
			}
			
			//更新redis存活时间
			redisService.expire(MyAuthenticationSuccessHandler.getToken(manager.getId(),manager.getIdentifying()), 30, TimeUnit.MINUTES);
			
		}else {//未登录
			request.setAttribute(ConstantCode.RBACSERVICE_REQUEST_ATTRIBUTE_KEY,StatusCodeEnum.NOT_LOGIN.getCode());
			return  hasPermission;
		}
		return  hasPermission;
	}
	
	
	//基于SecurityContextHolder，但是集群负载均衡下，SecurityContextHolder.getContext()存在获取不到用户信息(a服务器执行登录，b服务器执行访问url，b服务器会让重新登录)，
	private boolean hasPermissionBySecurityContextHolder(HttpServletRequest request,Authentication authentication,
			boolean hasPermission) {
		Object principal = authentication.getPrincipal();
		
		String urlRecord = "";
		
		if (principal instanceof UserDetails) { // 首先判断先当前用户是否是我们UserDetails对象。
			
			UserDetails  userDetails = (UserDetails) principal;
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();// 得到该用户所有权限
			if(authorities == null  || authorities.size() == 0){
				return  hasPermission;
			}
			
			urlRecord = request.getRequestURI();//请求url
			// 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
			for (GrantedAuthority authoritie : authorities) {
				if (antPathMatcher.match(authoritie.getAuthority(), urlRecord)) {
					hasPermission = true;
					break;
				}
			}	
		}
		return  hasPermission;
		
	}
}