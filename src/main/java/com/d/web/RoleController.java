package com.d.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.d.entity.Role;
import com.d.service.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * @author d
 */
@Controller
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;

	@GetMapping(path = "/admin/role/list")
	public String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
			Model model) {
		PageInfo<Role> pageInfo = roleService.findAllPage(pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "/admin/role/list";
	}
}
