package com.yd.config.security;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.constant.StatusCodeEnum;
import com.yd.pojo.Manager;
import com.yd.pojo.redis.RedisManager;
import com.yd.rest.ResultData;
import com.yd.service.ManagerService;
import com.yd.service.RedisService;
import com.yd.util.JsonUtils;

/**
 * 登录成功处理器
 * @author FelixChen
 *
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
      
      @Autowired
      private ObjectMapper objectMapper;
      @Autowired
  	  private  RedisService  redisService;
      @Autowired
      private  ManagerService   managerService;

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                  throws IOException, ServletException {            
    	  
    	  Manager manager = (Manager) authentication.getPrincipal();
    	  
    	  
    	//用户信息保存到redis，因为如果是集群负载均衡下，SecurityContextHolder.getContext()存在获取不到用户信息(a服务器执行登录，b服务器执行访问url，b服务器会让重新登录)
    	//如果不适用集群负载均衡，直接把这段注释掉就行
         String token = saveManagerToRedis(manager);
    	  
    	  
            //什么都不做的话，那就直接调用父类的方法,该方法会直接重定向到之前被拦截的方法
//            super.onAuthenticationSuccess(request, response, authentication);  
            //这里可以根据实际情况，来确定是跳转到页面或者json格式。
            //如果是返回json格式，那么我们这么写
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResultData.one(token)));
            
            //如果是要跳转到某个页面的，比如我们的那个index，二选一
//            new DefaultRedirectStrategy().sendRedirect(request, response, "/manager/index");
            
      }
      
    //1根据管理员id，得到管理员信息，通过id+updateTime(使用MD5加密)从redis获取用户信息，如果存在用户，更新用户异地登录
    //2更新用户登录时间
    //3保存用户信息到redis中，返回token(id+updateTime使用MD5加密)
  	private String saveManagerToRedis(Manager userInfo) {
  		//1查看账号是否已登录
  		String oldToken = getToken(userInfo.getId(),userInfo.getIdentifying());
  		String redisManagerStr = redisService.get(oldToken);
  		if(StringUtils.isNotBlank(redisManagerStr)){
  			//修改账号异地登录
  			RedisManager managerInfo = JsonUtils.jsonToPojo(redisManagerStr, RedisManager.class);
  			managerInfo.setRemoteLogin(true);
  			redisService.set(oldToken, JsonUtils.objectToJson(managerInfo));
  		}
  		
  		//2执行更新用户操作
  		String  identifying = UUID.randomUUID().toString().replace("-", "");
  		userInfo.setIdentifying(identifying);
  		userInfo.setGmtModified(new Date());
  		managerService.alterManagerByIdSelective(userInfo);
  		
  		//3保存用户信息到redis，
  		String newToken = getToken(userInfo.getId(),identifying);
  		RedisManager  redisManager = new RedisManager();
  		BeanUtils.copyProperties(userInfo, redisManager);
  		redisService.set(newToken, JsonUtils.objectToJson(redisManager));
  		redisService.expire(newToken, 30, TimeUnit.MINUTES);
  		return  newToken;
  	}
      
  	//token生成策略
  	public static  String  getToken(Integer  id,String  identifying){
  		return  DigestUtils.md5DigestAsHex((id+":"+identifying).getBytes());
  	}
}