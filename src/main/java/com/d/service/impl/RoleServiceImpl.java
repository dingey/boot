package com.d.service.impl;

import com.d.base.AbstractServiceImpl;
import com.d.dto.RoleDTO;
import com.d.entity.Role;
import com.d.entity.RolePermission;
import com.d.mapper.RoleMapper;
import com.d.mapper.RolePermissionMapper;
import com.d.service.RoleService;
import com.di.kit.FilterUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<RoleMapper, Role, RoleService> implements RoleService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    public List<Role> listByUserId(Integer userId) {
        return mapper.listByUserId(userId);
    }

    public PageInfo<Role> pageByName(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.listByName(name));
    }

    public Integer saveRole(RoleDTO role) {
        int i = save(role.getRole());
        List<RolePermission> list = rolePermissionMapper.listByRoleId(role.getRole().getId());
        List<RolePermission> adds = FilterUtil.differenceSet(role.getPermissions(), list, "permissionId");
        List<RolePermission> dels = FilterUtil.differenceSet(list, role.getPermissions(), "permissionId");
        for (RolePermission rp : adds) {
            rp.setRoleId(role.getRole().getId());
            rolePermissionMapper.insertSelective(rp);
        }
        for (RolePermission rp : dels) {
            rolePermissionMapper.delete(rp);
        }
        return i;
    }
}
