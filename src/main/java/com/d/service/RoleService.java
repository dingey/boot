package com.d.service;


import com.d.base.AbstractService;
import com.d.dto.RoleDTO;
import com.d.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author d
 */
public interface RoleService extends AbstractService<Role, RoleService> {
    List<Role> listByUserId(Integer userId);

    PageInfo<Role> pageByName(String name, int pageNum, int pageSize);

    Integer saveRole(RoleDTO role);
}
