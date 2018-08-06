package com.yd.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.constant.StatusCodeEnum;
import com.yd.rest.ResultData;


/**
 * 登录失败的
 * @author FelixChen
 *
 */
@Component("myAuthenticationFailHander")
public class MyAuthenticationFailHander extends SimpleUrlAuthenticationFailureHandler {
      @Autowired
      private ObjectMapper objectMapper;
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                  AuthenticationException exception) throws IOException, ServletException {
            //以Json格式返回
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResultData.error(StatusCodeEnum.USERNAME_OR_PASSWORD_ERROR)));
            //也可以转发(重定向)到指定页面，二选一
//            new DefaultRedirectStrategy().sendRedirect(request, response, "/manager/error");
            
      }
}