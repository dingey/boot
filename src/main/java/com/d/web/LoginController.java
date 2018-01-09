package com.d.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d.entity.User;
import com.d.service.PermissionService;
import com.d.service.UserService;

/**
 * @author d
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private UserService userService;

	@GetMapping(path = {"/","/admin/login"})
	public String login() {
		return "/admin/login";
	}

	/**
	 * 验证用户名和密码
	 * 
	 * @param String username,String password
	 * @return
	 */
	@PostMapping(value = "/admin/login")
	@ResponseBody
	public String checkLogin(String username, String password, @RequestParam(defaultValue = "false") boolean remeber) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			// 使用shiro来验证
			token.setRememberMe(true);
			currentUser.login(token);// 验证角色和权限
			User user = userService.getByUsername(username);
			currentUser.getSession(true).setAttribute("permissions", permissionService.listByUserId(user.getId()));
		}
		return "success";
	}

	/**
	 * 退出登录
	 */
	@PostMapping(value = "/admin/logout")
	@ResponseBody
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "success";
	}
}
