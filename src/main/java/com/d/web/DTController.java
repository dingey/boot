package com.d.web;

import com.d.entity.Man;
import com.d.service.ManService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DTController {
    @Autowired
    ManService manService;

    @ResponseBody
    @GetMapping(path = "/data.json")
    public PageInfo<Man> list(Man man, @RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {
        PageHelper.offsetPage(start, length);
        PageInfo<Man> pageInfo = new PageInfo<>(manService.list(man));
        return pageInfo;
    }

    @GetMapping(path = "/man")
    public String man(Man man, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        if(man.getName()=="")
            man.setName(null);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Man> pageInfo = new PageInfo<>(manService.list(man));
        model.addAttribute("pageInfo", pageInfo);
        return "/admin/man";
    }
}
