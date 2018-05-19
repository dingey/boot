package com.d.web;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "宠物", description = "宠物接口")
@RestController
public class PetController {
	@ApiOperation(value = "列表", notes = "查询宠物列表")
	@GetMapping(path = "/pet/list")
	public Object list(@ApiParam("页码") @RequestParam(defaultValue = "1") int page,
			@ApiParam("每页大小") @RequestParam(defaultValue = "5") int size) {
		return Arrays.asList(new Pet(1, "alice"));
	}

	@ApiOperation(value = "编辑", notes = "宠物编辑信息")
	@GetMapping(path = "/pet/edit")
	public Object edit(@ApiParam("宠物id") @RequestParam(defaultValue = "1") int id, HttpSession session,
			HttpServletRequest req, HttpServletResponse resp, Model model) {
		return new Pet(1, "alice");
	}

	@ApiOperation(value = "删除", notes = "宠物删除", response = Result.class)
	@GetMapping(path = "/pet/del")
	public Object del(@ApiParam("宠物id") @RequestParam(defaultValue = "1") int id) {
		return Result.success();
	}

	@ApiOperation(value = "保存", notes = "宠物保存", response = Pet.class)
	@PostMapping(path = "/pet/save")
	public Object save(@RequestBody Pet pet) {
		return Result.success(pet);
	}

	@ApiModel("宠物")
	public static class Pet {
		@ApiModelProperty("宠物id")
		Integer id;
		@ApiModelProperty("宠物名称")
		String name;

		public Pet() {
		}

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
