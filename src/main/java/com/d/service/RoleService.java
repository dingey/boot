package com.d.service;

import java.util.List;
import java.util.concurrent.locks.Lock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import com.d.base.BaseService;
import com.d.dto.RoleDTO;
import com.d.entity.Role;
import com.d.entity.RolePermission;
import com.d.mapper.RoleMapper;
import com.d.mapper.RolePermissionMapper;
import com.di.kit.FilterUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author d
 */
@Service
public class RoleService extends BaseService<RoleMapper, Role, RoleService> {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RedisConnectionFactory connectionFactory;
    private RedisLockRegistry registry;

    @PostConstruct
    private void init() {
        registry = new RedisLockRegistry(connectionFactory, "lock_role");
    }

    public List<Role> listByUserId(Integer userId) {
        return mapper.listByUserId(userId);
    }

    public PageInfo<Role> pageByName(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.listByName(name));
    }

    public Integer saveRole(RoleDTO role) {
        Lock lock = registry.obtain("saveRole-" + role.getRole().getId());
        int i = 0;
        if (lock.tryLock()) {
            try {
                i = super.save(role.getRole());
                List<RolePermission> list = rolePermissionMapper
                        .listByRoleId(role.getRole().getId());
                List<RolePermission> adds = FilterUtil.differenceSet(
                        role.getPermissions(), list, "permissionId");
                List<RolePermission> dels = FilterUtil.differenceSet(list,
                        role.getPermissions(), "permissionId");
                for (RolePermission rp : adds) {
                    rp.setRoleId(role.getRole().getId());
                    rolePermissionMapper.insertSelective(rp);
                }
                for (RolePermission rp : dels) {
                    rolePermissionMapper.delete(rp);
                }
            } finally {
                lock.unlock();
            }
        }
        return i;
    }
}
