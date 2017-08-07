package com.d.web;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author d
 */
@Controller
public class IndexController {
	@Resource
	private MessageSource messageSource;

	@RequestMapping(path = { "", "/", "/index" })
	public String index(Model m) {
		Locale locale = LocaleContextHolder.getLocale();
		String welcome = messageSource.getMessage("welcome", null, locale);
		m.addAttribute("name", "alice");
		m.addAttribute("welcome1", welcome);
		return "index";
	}
}
