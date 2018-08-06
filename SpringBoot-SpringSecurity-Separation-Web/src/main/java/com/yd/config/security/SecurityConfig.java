package com.yd.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author FelixChen
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//登录url
	private final String  LOGIN_URL = "/managerLogin";
	
	@Autowired
	private  AccessDeniedHandler   myAccessDeniedHandler;
	
	@Autowired
	private  AuthenticationProvider  authenticationProvider;
	@Autowired
	@Qualifier("myAuthenticationSuccessHandler")
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	@Qualifier("myAuthenticationFailHander")
    private AuthenticationFailureHandler myAuthenticationFailHander;
	@Autowired
	@Qualifier("myLoginUrlAuthenticationEntryPoint")
	private   LoginUrlAuthenticationEntryPoint  myLoginUrlAuthenticationEntryPoint;
	@Autowired
	UserDetailsService userDetailsService;  
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//登出
		http.logout()
        .logoutUrl("/manager/logout")//设置登出处理的url  
        .logoutSuccessUrl("/manager/index");//设置登出成功后跳转到首页，默认是跳转到登录页面  
        
		
		
		//登录
		http.formLogin().loginProcessingUrl(LOGIN_URL)
		.successHandler(myAuthenticationSuccessHandler)//登录成功后的操作
//		.failureUrl("/manager/error")
		.failureHandler(myAuthenticationFailHander)//登录失败之后的操作
		.permitAll();
		
		
		//静态资源不拦截，默认查找classpath下的，详情请看笔记web配置Thymeleaf和静态文件
		http.authorizeRequests().antMatchers(new String[]{"*.js","*.css","/img/**","/images/**","/fonts/**","/**/favicon.ico","/**/*.html","/druid/**",}).permitAll()
		.and().authorizeRequests().anyRequest().access("@rbacService.hasPermission(request,authentication)")//必须经过认证才可以访问，自定义类
//		.and().cors()//听说是解决跨域问题，未测试
        .and()
        .csrf().disable();
		
		//未登录返回json数据，默认是跳转登录html
		http.exceptionHandling().authenticationEntryPoint(myLoginUrlAuthenticationEntryPoint);
		//没有权限的处理器，返回json数据
		http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
		//以下这句就可以控制单个用户只能创建一个session，也就只能在服务器登录一次,UserDetails实现类必须要重写hashCode,equals,toString三个方法
//        http.sessionManagement().maximumSessions(1).expiredUrl("/login.html");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(authenticationProvider);
		 //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
//		 auth
//         .inMemoryAuthentication()
//         		//自定义账号和密码，默认账号密码不起作用了
//               .withUser("admin").password("123456").roles("USER")
//               .and()
//               .withUser("yd").password("123").roles("ADMIN");
	}
	
	
	/**
	 * 创建未登录处理器
	 * @return
	 */
	@Bean("myLoginUrlAuthenticationEntryPoint")
    public LoginUrlAuthenticationEntryPoint getMyLoginUrlAuthenticationEntryPoint() {
        return new MyLoginUrlAuthenticationEntryPoint(LOGIN_URL);
    }
	
//	@Autowired
//	private  DataSource  dataSource;
//	/**
//     * 记住我功能的token存取器配置，好像没作用
//     * @return
//     */
//    @Bean
//    public JdbcTokenRepositoryImpl tokenRepository() {
//          JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//          tokenRepository.setDataSource(dataSource);
//          return tokenRepository;
//    }
}
