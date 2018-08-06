package com.yd.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.constant.ConstantCode;
import com.yd.constant.StatusCodeEnum;
import com.yd.rest.ResultData;

/**
 * 权限不足处理器
 * @author yd
 *
 */
@Component("myAccessDeniedHandler")
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		ResultData<Object> errorInfo = ResultData.error(StatusCodeEnum.PERMISSION_DENIED);
		
		//获取错误码
		String errorCode = (String)request.getAttribute(ConstantCode.RBACSERVICE_REQUEST_ATTRIBUTE_KEY);
		if(StringUtils.isNotBlank(errorCode)){
			if(errorCode.equals(StatusCodeEnum.INVALID_TOKEN.getCode())){//token无效
				errorInfo = ResultData.error(StatusCodeEnum.INVALID_TOKEN);
			}else if (errorCode.equals(StatusCodeEnum.MANAGER_REMOTE_LOGIN.getCode())) {//异地登录
				errorInfo = ResultData.error(StatusCodeEnum.MANAGER_REMOTE_LOGIN);
			}else if (errorCode.equals(StatusCodeEnum.NOT_LOGIN.getCode())) {//未登录
				errorInfo = ResultData.error(StatusCodeEnum.NOT_LOGIN);
			}
		}
		
		response.getWriter().write(objectMapper.writeValueAsString(errorInfo));
		
		
		
		
	}

}
