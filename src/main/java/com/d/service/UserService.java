package com.d.service;

import org.springframework.stereotype.Service;

import com.d.base.BaseService;
import com.d.entity.User;
import com.d.mapper.UserMapper;

/**
 * @author d
 */
@Service
public class UserService extends BaseService<UserMapper, User> {

	public User getByUsername(String username) {
		return mapper.getByUsername(username);
	}

}
