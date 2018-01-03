package com.d.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.d.base.BaseService;
import com.d.entity.Role;
import com.d.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author d
 */
@Service
public class RoleService extends BaseService<RoleMapper, Role> {
	public List<Role> listByUserId(Integer userId) {
		return mapper.listByUserId(userId);
	}

	public PageInfo<Role> pageByName(String name, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(mapper.listByName(name));
	}
}
