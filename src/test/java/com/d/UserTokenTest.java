package com.d;

import org.junit.Before;
import org.junit.Test;

import com.d.util.DesUtil;
import com.d.util.UserToken;

public class UserTokenTest {
	UserToken u;

	@Before
	public void init() {
		u = new UserToken();
		u.setId(123456789012345678L);
		u.setExpired(System.currentTimeMillis() / 1000);
		System.out.println("原始[" + u.toString().length() + "] : " + u);
	}

	@Test
	public void aes() {
		String tokenStringAES = u.tokenStringAES();
		System.out.println("AES[" + tokenStringAES.length() + "] : " + tokenStringAES);
		UserToken aes = UserToken.fromTokenStringAES(tokenStringAES);
		System.out.println(aes);
	}

	@Test
	public void des() {
		String tokenStringDES = u.tokenStringDES();
		System.out.println("DES[" + tokenStringDES.length() + "] : " + tokenStringDES);
		UserToken des = UserToken.fromTokenStringDES(tokenStringDES);
		System.out.println(des);

		String des2 = DesUtil.encrypt(tokenStringDES);
		System.out.println("DES[" + des2.length() + "] : " + des2);
		UserToken des22 = UserToken.fromTokenStringDES(DesUtil.decrypt(des2));
		System.out.println(des22);
	}
}
