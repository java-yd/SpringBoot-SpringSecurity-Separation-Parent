package com.yd.config.security;

import java.util.Collection;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.yd.constant.StatusCodeEnum;
import com.yd.pojo.Manager;
import com.yd.service.RedisService;

/**
 * 登录操作，必须是post请求
 * @author FelixChen
 *
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	/**
     * 注入我们自己定义的用户信息获取对象
     */
    @Autowired
    private UserDetailsService userDetailService;
    
	
    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
        // 这里构建来判断用户是否存在和密码是否正确
        Manager userInfo = (Manager) userDetailService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
        if (userInfo == null) {
              throw new BadCredentialsException(StatusCodeEnum.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }


        // //这里我们还要判断密码是否正确，实际应用中，我们的密码一般都会加密，以Md5加密为例，设置密码时，也要使用该对象
        //springboot 2.0.2使用 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//         Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
        // //这里第个参数，是salt
        // 就是加点盐的意思，这样的好处就是用户的密码如果都是123456，由于盐的不同，密码也是不一样的,由于数据库保存的密码未加盐，所以不加盐
//         String encodePwd = md5PasswordEncoder.encodePassword(password, null);
        // //这里判断密码正确与否
        String encodePwd = DigestUtils.md5DigestAsHex(password.getBytes());
         if(!userInfo.getPassword().equals(encodePwd))
         {
        	 throw new BadCredentialsException(StatusCodeEnum.USERNAME_OR_PASSWORD_ERROR.getMessage());
         }
         
        // //这里还可以加一些其他信息的判断，比如用户账号已停用等判断，由于系统未涉及到，我就不用判断了。
        //
         
         
        //这里是没查数据库时候固定操作
//        if (!userInfo.getPassword().equals("123456")) {
//              throw new BadCredentialsException("密码不正确");
//        }
        
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token，有登录管理员的信息
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
        
//        SecurityContextHolder.getContext();//这里可以看到token信息,全局都可以使用该方法获取
        return token;
	}

	


	@Override
	public boolean supports(Class<?> arg0) {
		// 这里直接改成retrun true;表示是支持这个执行
        return true;
	}
}
