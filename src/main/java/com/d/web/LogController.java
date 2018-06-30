package com.d.web;

import com.d.entity.Log;
import com.d.service.LogService;
import com.d.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "日志", description = "日志接口")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    @GetMapping(path = "/log/{id}")
    @ApiOperation(value = "查询", notes = "查询")
    public Log get(@PathVariable("id") @ApiParam(defaultValue = "1") Integer id) {
        return logService.get(id);
    }

    @DeleteMapping(path = "/log/{id}")
    @ApiOperation(value = "删除", notes = "删除日志")
    public Integer del(@PathVariable("id") @ApiParam(defaultValue = "1") Integer id) {
        return logService.delete(id);
    }

    @PostMapping(path = "/log")
    @ApiOperation(value = "保存", notes = "保存日志")
    public Integer save(Log log) {
        return logService.save(log);
    }
}
