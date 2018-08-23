package com.d.service.impl;

import com.d.base.AbstractServiceImpl;
import com.d.entity.Man;
import com.d.mapper.ManMapper;
import com.d.service.ManService;
import org.springframework.stereotype.Service;

@Service
public class ManServiceImpl extends AbstractServiceImpl<ManMapper, Man, ManService> implements ManService {
}
