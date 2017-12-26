package com.d.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.d.entity.Permission;
import com.d.service.PermissionService;

/**
 * @author d
 */
@Controller
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@GetMapping(path = "/admin/permission/list")
	public String list(Model model) {
		List<Permission> list = permissionService.findAll();
		model.addAttribute("list", list);
		return "/admin/permission/list";
	}

	@GetMapping(path = "/admin/permission/edit")
	public String edit(Integer id, Model model) {
		List<Permission> list = permissionService.findAll();
		Permission permission = permissionService.get(id);
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		List<String> mappings = new ArrayList<>();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			HandlerMethod handlerMethod = m.getValue();
			if (handlerMethod.hasMethodAnnotation(RequiresPermissions.class)) {
				String[] value = handlerMethod.getMethodAnnotation(RequiresPermissions.class).value();
				boolean exists = false;
				for (Permission p : list) {
					if (p.getPermission().equals(value[0])) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					mappings.add(value[0]);
				}
			}
		}
		if (permission != null) {
			mappings.add(permission.getPermission());
		}
		model.addAttribute("permission", permission);
		model.addAttribute("mappings", mappings);
		model.addAttribute("list", list);
		return "/admin/permission/edit";
	}

	@ResponseBody
	@PostMapping(path = "/admin/permission/save")
	public String save(Permission permission) {
		return "success";
	}
}
