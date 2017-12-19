package com.d.web;

import java.util.List;

import com.d.entity.User;
import com.d.mapper.UserMapper;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@GetMapping("/user/list")
	public List<User> getUsers() {
		List<User> users = userMapper.getAll();
		return users;
	}

	@RequiresPermissions("user:show")
	@GetMapping("/user/get")
	public User getUser(Long id) {
		User user = userMapper.getById(id);
		return user;
	}

	@PostMapping("/user/add")
	public void save(User user) {
		userMapper.insert(user);
	}

	@PostMapping(value = "/user/update")
	public void update(User user) {
		userMapper.update(user);
	}

	@DeleteMapping(value = "/user/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		// userMapper.delete(id);
	}

}