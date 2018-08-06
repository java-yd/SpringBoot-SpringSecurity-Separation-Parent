package com.yd.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/**
 * 返回权限验证的接口
 * 
 *
 */
public interface RbacService {
      boolean hasPermission(HttpServletRequest request,Authentication authentication);
}

