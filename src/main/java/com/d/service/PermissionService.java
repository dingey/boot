package com.d.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.d.base.BaseService;
import com.d.entity.Permission;
import com.d.mapper.PermissionMapper;

/**
 * @author d
 */
@Service
public class PermissionService extends BaseService<PermissionMapper, Permission> {
	public List<Permission> listByRoleId(Integer roleId) {
		return mapper.listByRoleId(roleId);
	}
}
