package com.yd.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yd.pojo.Manager;
import com.yd.service.ManagerService;

/**
 * 实现UserService 来返回这个UserInfo的对象实例，并交给spring管理
 * @author FelixChen
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private  ManagerService  managerService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，并构建成我们自己的UserInfo来返回。
		Manager  user = managerService.findManagerByUserName(username);
		
		//这里我们先模拟下,后续我们用数据库来实现
//        if(username.equals("admin"))
//        {
//              //假设返回的用户信息如下;
//              UserInfo userInfo=new UserInfo("admin", "123456", "ROLE_ADMIN", true,true,true, true);
//              return userInfo;
//        }
//		UserInfo  userInfo = new  UserInfo(user.getUsername(),user.getPassword(),user.getRole(),user.getAccountNonExpired(),
//				user.getAccountNonLocked(),user.getCredentialsNonExpired(),user.getEnabled());
		return (UserDetails) user;
	}

}
