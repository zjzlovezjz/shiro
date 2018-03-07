package com.atguigu.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class ShiroRealm extends  AuthorizingRealm {

	//实现认证的方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");
		//System.out.println("doGetAuthenticationInfo:"+token.hashCode());
		
		//1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		//2.从UsernamePasswordToken获取username
		String username = upToken.getUsername();
		
		//3.调用数据库的方法，从数据库中查询username对应的用户记录
		System.out.println("从数据库中获取username:"+username+"所对应的用户信息.");
		
		//4.若用户不存在，则可以抛出UnknownAccountException异常
		if("unknown".equals(username)){
			throw new UnknownAccountException("用户不存在！");
		}
		
		//5.根据用户情况，决定是否需要抛出其他的AuthenticationException异常
		if("monster".equals(username)){
			throw new LockedAccountException("用户被锁定！");
		}
		
		//6.根据用户的情况，来构建AuthenticationInfo对象并返回 因为返回类型是一个借口，
		//通常使用的实现类为：SimpleAuthenticationInfo
		//以下信息是从数据库中获取的
		//1).principal：认证的实体信息，可以是username,也可以是数据表对应的用户的实体类对象
		Object principal = username;
		//2).credentials:密码
		Object credentials = null;  // "fc1709d0a95a6be30bc5926fdb7f22f4";  //密码的比对是shiro自己帮我们完成的
		if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		
		//3).realmName:当前realm对象的name,调用父类的getName()方法即可
		String realmName = getName();
		//4).盐值,用于盐值加密,一般拿唯一的值来做盐值，比如用户名
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}
	
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("user");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	//实现授权方法，授权会被shiro回调的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 测试访问需要被授权的页面看看能不能跑通过
		//System.out.println("doGetAuthorizationInfo...");
		
		//1.从PrincipalCollection获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();
		
		//2.利用登录用户的信息来获取当前用户的角色或者权限（可能需要查询数据库）
		//user只能拥有user的权限，认证后就可以有，但是admin认证成功后不仅有user的权限，还有自己的权限
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}
		
		//3.创建SimpleAuthorizationInfo(返回类型接口的实现类)，并设置roles属性
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		
		//4.返回SimpleAuthorizationInfo对象
		return info;
	}
	
	
}
