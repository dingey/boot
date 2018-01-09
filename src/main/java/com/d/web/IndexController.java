package com.d.web;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.d.entity.User;

/**
 * @author d
 */
@Controller
public class IndexController extends BaseController {
	@Resource
	private MessageSource messageSource;

	@RequestMapping(path = {"/admin/index"})
	public String index(Model m) {
		return "admin/index";
	}

	@RequestMapping(path = "/nulls")
	public String nulls(Model m) {
		m.addAttribute("n1", null);
		m.addAttribute("n2", new Integer(0));
		m.addAttribute("user", new User());
		HashMap<String, Object> map1 = new HashMap<>();
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("name", null);
		map2.put("user", new User());
		map1.put("map", map2);
		m.addAttribute("map", map1);
		return "admin/nulls";
	}
}
