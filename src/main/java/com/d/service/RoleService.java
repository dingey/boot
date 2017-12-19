package com.d.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.d.base.BaseService;
import com.d.entity.Role;
import com.d.mapper.RoleMapper;

/**
 * @author d
 */
@Service
public class RoleService extends BaseService<RoleMapper, Role> {
	public List<Role> listByUserId(Integer userId){
		return mapper.listByUserId(userId);
	}
}
