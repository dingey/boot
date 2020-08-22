package com.d.service.impl;

import com.d.base.AbstractServiceImpl;
import com.d.entity.Log;
import com.d.mapper.LogMapper;
import com.d.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends AbstractServiceImpl<LogMapper, Log, LogService> implements LogService {
}
