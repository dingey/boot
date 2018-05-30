package com.d.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.d.service.IPay;
import com.d.service.IPay.CreatePayException;
import com.d.util.Result;

@RestController
public class PayController {
	Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	IPay iPay;

	@GetMapping(path = "/pay/create/{type}")
	public Result createPay(@PathVariable("type") Integer payType, String title,
			String content, Integer amount) {
		try {
			iPay.createPay(title, content, amount);
		} catch (CreatePayException e) {
			e.printStackTrace();
		}
		return Result.success();
	}

	@RequestMapping(path = "/pay/notify/{type}/{id}", method = {
			RequestMethod.GET, RequestMethod.POST})
	public String notyfiHandle() {
		return null;
	}
}
