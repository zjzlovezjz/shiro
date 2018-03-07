package com.atguigu.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

	public LinkedHashMap<String, String> buildfilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/login.jsp","anon");
		map.put("/shiro/login","anon");
		map.put("/shiro/logout","logout");
		map.put("/user.jsp","authc,roles[user]");
		map.put("/admin.jsp","authc,roles[admin]");
		//ͨ�� ��ס�һ�����֤��¼�� �����Է���list.jspҳ��
		map.put("/list.jsp", "user");
		map.put("/**","authc");
		return map;
	}
}
