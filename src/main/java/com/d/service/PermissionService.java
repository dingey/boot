package com.d.service;

import com.d.base.AbstractService;
import com.d.dto.PermissionDTO;
import com.d.entity.Permission;

import java.util.List;

/**
 * @author d
 */
public interface PermissionService extends AbstractService<Permission, PermissionService> {
    List<Permission> listByRoleId(Integer roleId);

    Integer updateOriginal(int parentId, int index);

    Integer updateTarget(int parentId, int index);

    Integer countByParentId(int parentId);

    List<Permission> listAuthc();

    List<PermissionDTO> listDto(int userId);
}
