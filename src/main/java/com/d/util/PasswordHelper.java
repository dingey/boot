package com.d.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.d.entity.User;

/**
 * @author d
 */
public class PasswordHelper {
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private static final String algorithmName = "md5";
	private final static int hashIterations = 2;

	public static void encryptPassword(User user) {
		// User对象包含最基本的字段Username和Password
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		// 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
		user.setPassword(newPassword);
	}

	public static void main(String[] args) {
		User u = new User();
		u.setUsername("admin");
		u.setPassword("admin");
		encryptPassword(u);
		System.out.println(u.getSalt());
		System.out.println(u.getPassword());
	}
}
