package com.d.web;

import com.d.entity.Log;
import com.d.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "日志", description = "日志接口")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    @GetMapping(path = "/log/{id}")
    @ApiOperation(value = "查询", notes = "查询")
    public Log get(@PathVariable("id") @ApiParam(defaultValue = "1") Integer id) {
        return logService.getCache(id);
    }

    @DeleteMapping(path = "/log/{id}")
    @ApiOperation(value = "删除", notes = "删除日志")
    public Integer del(@PathVariable("id") @ApiParam(defaultValue = "1") Integer id) {
        return logService.deleteCache(id);
    }

    @PostMapping(path = "/log")
    @ApiOperation(value = "保存", notes = "保存日志")
    public Integer save(Log log) {
        return logService.saveCache(log);
    }

    @PostMapping(path = "/logs")
    @ApiOperation(value = "保存", notes = "保存日志")
    public Integer save(LogForm logs) {
        int i = 0;
        for (Log l : logs.getLogs()) {
            i += logService.saveCache(l);
        }
        return i;
    }

    static class LogForm{
        List<Log> logs;

        public List<Log> getLogs() {
            return logs;
        }

        public void setLogs(List<Log> logs) {
            this.logs = logs;
        }
    }
}
