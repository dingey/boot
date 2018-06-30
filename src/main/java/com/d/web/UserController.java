package com.d.web;

import com.d.entity.Role;
import com.d.entity.User;
import com.d.service.RoleService;
import com.d.service.UserService;
import com.github.pagehelper.PageInfo;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "用户", description = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/user/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,
                       Model model) {
        PageInfo<User> pageInfo = userService.pageAll(pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "/admin/user/list";
    }

    @GetMapping("/admin/user/edit")
    public String edit(Integer id, Model model) {
        User user = userService.get(id);
        List<Role> userRoles = roleService.listByUserId(id);
        List<Role> roles = roleService.listAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("userRoles", userRoles);
        return "/admin/user/edit";
    }

    @ResponseBody
    @PostMapping("/admin/user/save")
    public String save(User user, Integer[] roleIds) {
        int i = userService.saveUser(user, roleIds);
        return i > 0 ? "success" : "fail";
    }

    @ResponseBody
    @GetMapping(value = "/user/{id}")
    @ApiOperation(value = "查询", notes = "查询")
    public Object get(@PathVariable("id") @ApiParam(defaultValue = "1") Integer id) {
        return userService.get(id);
    }

    @ResponseBody
    @DeleteMapping(value = "/user/{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public Object delete(@PathVariable("id") @ApiParam(defaultValue = "1") Integer id) {
        return userService.delete(id);
    }

}