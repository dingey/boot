package com.d.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.d.entity.Log;
import com.d.util.JsonUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d.dto.PermissionDTO;
import com.d.service.PermissionService;

/**
 * @author d
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private MessageSource messageSource;
    @Resource
    private PermissionService permissionService;

    @RequestMapping(path = "/admin/index")
    public String index(Model m) {
        List<PermissionDTO> listDto = permissionService.listDto(getUser().getId());
        m.addAttribute("list", listDto);
        return "admin/index";
    }

    @ResponseBody
    @RequestMapping(path = "/hi")
    public String hi(@RequestParam(defaultValue = "guest") String name) {
        return "hi " + name + ",server time is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @ResponseBody
    @RequestMapping(path = "/json")
    public String json(@RequestBody Log log) {
        return "you send : " + JsonUtil.singleton().toJson(log);
    }

    @ResponseBody
    @RequestMapping(path = "/xml", consumes = "text/xml")
    public String xml(@RequestBody Log log) {
        return "you send : " + JsonUtil.singleton().toJson(log);
    }
}
