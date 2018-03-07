package com.atguigu.shiro.services;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

public class ShiroService {

	@RequiresRoles(value = {"admin"})
	public void testMethod(){
		System.out.println("testMethod,time:"+new Date());
		//»ñÈ¡shiroµÄsession
		Session session = SecurityUtils.getSubject().getSession();
		Object val = session.getAttribute("key");
		System.out.println("Service SessionVal:"+val);
	}
}
