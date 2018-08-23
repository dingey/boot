package com.d.web;

import com.d.entity.Man;
import com.d.service.ManService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DTController {
    @Autowired
    ManService manService;

    @GetMapping(path = "/data.json")
    public PageInfo<Man> list(Man man, @RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer length) {
        PageHelper.offsetPage(start, length);
        PageInfo<Man> pageInfo = new PageInfo<>(manService.list(man));
        return pageInfo;
    }
}
