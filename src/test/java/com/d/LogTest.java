package com.d;

import com.d.entity.Log;
import com.d.mapper.LogMapper;
import com.d.service.LogService;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class LogTest extends ApplicationTests {
    @Autowired
    private LogService logService;
    @Autowired
    private LogMapper logMapper;

    @Override
    public void test() {
        //execute();
        //sql();
    }

    void sql() {
        SQL s = new SQL().SELECT("*").FROM("log");
        List<LinkedHashMap> linkedHashMaps = logMapper.listBySQL(s);
        System.out.println(linkedHashMaps.size());
        s = new SQL().SELECT("*").FROM("log").WHERE("id=1");
        LinkedHashMap bySQL = logMapper.getBySQL(s);
        s = new SQL().SELECT("content","line_num").FROM("log").WHERE("id=2");
        Integer count = logMapper.countBySQL(s);
    }

    void execute() {
        long s1 = System.currentTimeMillis();
        Log log = new Log();
        log.setContent("admin");
        List<Log> list = logService.list(log);
        Integer count = logService.count(log);
        Log log1 = logService.get(log);
        System.out.println(count);
        System.out.println(list.size());
        log.setCause("asa");
        Integer count2 = logService.count(log);
        System.out.println(count2);
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
        List<Log> logs = logService.listByIdsCache(Arrays.asList(1, 2));
        System.out.println(logs.size());
    }
}
