package com.d.util;

public class UserToken {
	// uid
	private Long id;
	// 过期时间秒
	private Long expired;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExpired() {
		return expired;
	}

	public void setExpired(Long expired) {
		this.expired = expired;
	}

	public boolean expired() {
		return expired != null && expired > 0 && expired < System.currentTimeMillis() / 1000;
	}

	public boolean valid() {
		return id != null && !expired();
	}

	public String tokenStringAES() {
		StringBuilder s = new StringBuilder();
		s.append(id).append(";").append(expired);
		return AESUtil.encrypt(s.toString());
	}

	public static UserToken fromTokenStringAES(String tokenStr) {
		UserToken ut = new UserToken();
		try {
			String[] ss = AESUtil.decrypt(tokenStr).split(";");
			ut.setId(Long.valueOf(ss[0]));
			ut.setExpired(Long.valueOf(ss[1]));
		} catch (Exception e) {
		}
		return ut;
	}

	public String tokenStringDES() {
		StringBuilder s = new StringBuilder();
		s.append(id).append(";").append(expired);
		return DesUtil.encrypt(s.toString());
	}

	public static UserToken fromTokenStringDES(String tokenStr) {
		UserToken ut = new UserToken();
		try {
			String[] ss = DesUtil.decrypt(tokenStr).split(";");
			ut.setId(Long.valueOf(ss[0]));
			ut.setExpired(Long.valueOf(ss[1]));
		} catch (Exception e) {
		}
		return ut;
	}

	@Override
	public String toString() {
		return "{id:" + id + ",expired:" + expired + "}";
	}

	public static void main(String[] args) {
		UserToken u = new UserToken();
		u.setId(123456789012345678L);
		u.setExpired(System.currentTimeMillis() / 1000);
		System.out.println("原始[" + u.toString().length() + "] : " + u);
		String tokenStringAES = u.tokenStringAES();
		System.out.println("AES[" + tokenStringAES.length() + "] : " + tokenStringAES);
		UserToken aes = fromTokenStringAES(tokenStringAES);
		System.out.println(aes);

		String tokenStringDES = u.tokenStringDES();
		System.out.println("DES[" + tokenStringDES.length() + "] : " + tokenStringDES);
		UserToken des = fromTokenStringDES(tokenStringDES);
		System.out.println(des);
	}
}
