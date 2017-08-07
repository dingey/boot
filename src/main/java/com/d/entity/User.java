package com.d.entity;

import java.io.Serializable;

import com.d.enums.UserSexEnum;
import com.d.util.SqlProvider.Id;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String userName;
	private String password;
	private UserSexEnum userSex;
	private String nickName;

	public User() {
		super();
	}

	public User(String userName, String password, UserSexEnum userSex) {
		super();
		this.password = password;
		this.userName = userName;
		this.userSex = userSex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserSexEnum getUserSex() {
		return userSex;
	}

	public void setUserSex(UserSexEnum userSex) {
		this.userSex = userSex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "userName " + this.userName + ", pasword " + this.password + "sex " + userSex.name();
	}

}