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
		
		// ��ȡ��ǰ�� Subject.
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
        	// ���û����������װΪ UsernamePasswordToken ����                      �û���                 ����
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            
            // rememberme����
            token.setRememberMe(true);
            
            try {
            	System.out.println("1."+token.hashCode());
            	// ִ�е�¼. ���token�ᴫ��ShiroRealm�����doGetAuthenticationInfo��������Ĳ���token
            	//�Լ��������ߴ�ӡ��ϣֵ��֤�������token����Realm�Ǳߵ�token
                currentUser.login(token);
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // ������֤ʱ�쳣�ĸ���. ����������쳣������������
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            	System.out.println("��¼ʧ�ܣ�"+ae.getMessage());
            }
        }
		return "redirect:/list.jsp";
	}
}
