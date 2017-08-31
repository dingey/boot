package com.d.web;

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

	@RequestMapping(path = { "", "/", "/index" })
	public String index(Model m) {
		return "admin/index";
	}

	@RequestMapping(path = "/nulls")
	public String nulls(Model m) {
		m.addAttribute("n1", null);
		m.addAttribute("n2", new Integer(0));
		m.addAttribute("user", new User());
		return "admin/nulls";
	}
}
