package com.d.web;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("宠物")
@RestController
public class PetController {
	@ApiOperation("列表")
	@GetMapping(path = "/pet/list")
	public Object list(@ApiParam("页码") @RequestParam(defaultValue = "1") int page,
			@ApiParam("每页大小") @RequestParam(defaultValue = "5") int size) {
		return Arrays.asList(new Pet(1, "alice"));
	}

	@ApiOperation("列表")
	@GetMapping(path = "/pet/edit")
	public Object edit(@ApiParam("主键") @RequestParam(defaultValue = "1") int id, HttpSession session,
			HttpServletRequest req, HttpServletResponse resp, Model model) {
		return new Pet(1, "alice");
	}

	@ApiOperation("删除")
	@GetMapping(path = "/pet/del")
	public Object del(@ApiParam("主键") @RequestParam(defaultValue = "1") int id) {
		return Result.success();
	}

	public static class Pet {
		Integer id;
		String name;

		public Pet(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
