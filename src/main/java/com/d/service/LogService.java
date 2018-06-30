package com.d.service;

import com.d.base.AbstractService;
import com.d.entity.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface LogService extends AbstractService<Log, LogService> {
}
