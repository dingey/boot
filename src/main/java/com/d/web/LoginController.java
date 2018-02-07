package com.d.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.d.entity.User;
import com.d.service.UserService;

/**
 * @author d
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;

	@GetMapping(path = { "", "/admin/login" })
	public String login() {
		return "/admin/login";
	}

	@PostMapping(value = "/admin/login")
	public String checkLogin(String username, String password, @RequestParam(defaultValue = "false") boolean remeber) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, remeber);
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			// 使用shiro来验证
			token.setRememberMe(true);
			currentUser.login(token);// 验证角色和权限
			User user = userService.getByUsername(username);
			currentUser.getSession(true).setAttribute("user", user);
		}
		return "redirect:/admin/index";
	}

	@GetMapping(value = "/admin/logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:/admin/login";
	}
}
