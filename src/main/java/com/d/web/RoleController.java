package com.d.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d.dto.RoleDTO;
import com.d.entity.Role;
import com.d.service.PermissionService;
import com.d.service.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * @author d
 */
@Controller
public class RoleController extends BaseController {
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;

	@GetMapping(path = "/admin/role/list")
	public String list(String name, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, Model model) {
		PageInfo<Role> pageInfo = roleService.pageByName(name, pageNum, pageSize);
		model.addAttribute("pageInfo", pageInfo);
		return "/admin/role/list";
	}

	@GetMapping(path = "/admin/role/edit")
	public String edit(int id, Model model) {
		model.addAttribute("role", roleService.get(id));
		model.addAttribute("list", permissionService.listAll());
		model.addAttribute("perms", permissionService.listByRoleId(id));
		return "/admin/role/edit";
	}

	@ResponseBody
	@PostMapping(path = "/admin/role/save")
	public String save(RoleDTO role) {
		int i = roleService.saveRole(role);
		return i > 0 ? "success" : "fail";
	}
}
