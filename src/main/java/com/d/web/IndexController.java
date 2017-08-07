package com.d.web;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
