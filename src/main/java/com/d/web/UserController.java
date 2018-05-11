package com.d.web;

import com.d.entity.User;
import com.d.mapper.UserMapper;
import com.d.service.UserService;
import com.github.pagehelper.PageInfo;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@GetMapping("/admin/user/list")
	public String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
			Model model) {
		PageInfo<User> pageInfo = userService.pageAll(pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "/admin/user/list";
	}

	@GetMapping("/admin/user/edit")
	public String edit(Integer id, Model model) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		return "/admin/user/edit";
	}

	@ResponseBody
	@PostMapping("/admin/user/save")
	public String save(User user) {
		int i = userService.save(user);
		return i > 0 ? "success" : "fail";
	}

	@RequiresPermissions("user:show")
	@GetMapping("/user/get")
	public User getUser(Long id) {
		User user = userMapper.getById(id);
		return user;
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