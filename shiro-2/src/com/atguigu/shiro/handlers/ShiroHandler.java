package com.atguigu.shiro.handlers;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.shiro.services.ShiroService;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "value12345");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}

	@RequestMapping("/login")
	public String login(@RequestParam("username") String username,@RequestParam("password") String password){
		
		// 获取当前的 Subject.
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为 UsernamePasswordToken 对象                      用户名                 密码
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            
            // rememberme功能
            token.setRememberMe(true);
            
            try {
            	System.out.println("1."+token.hashCode());
            	// 执行登录. 这个token会传到ShiroRealm里面的doGetAuthenticationInfo方法里面的参数token
            	//自己可以两边打印哈希值验证，这里的token就是Realm那边的token
                currentUser.login(token);
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类. 上面的三个异常都是它的子类
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("登录失败："+ae.getMessage());
            }
        }
		return "redirect:/list.jsp";
	}
}
