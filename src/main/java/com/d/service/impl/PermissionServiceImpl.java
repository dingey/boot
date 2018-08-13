package com.d.service.impl;

import com.d.base.AbstractServiceImpl;
import com.d.dto.PermissionDTO;
import com.d.entity.Permission;
import com.d.mapper.PermissionMapper;
import com.d.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends AbstractServiceImpl<PermissionMapper, Permission, PermissionService> implements PermissionService {
    public List<Permission> listByRoleId(Integer roleId) {
        return mapper.listByRoleId(roleId);
    }

    @Override
    public List<Permission> listAll() {
        return mapper.listNormal();
    }

    public Integer updateOriginal(int parentId, int index) {
        return mapper.updateOriginal(parentId, index);
    }

    public Integer updateTarget(int parentId, int index) {
        return mapper.updateTarget(parentId, index);
    }

    public Integer countByParentId(int parentId) {
        return mapper.countByParentId(parentId);
    }

    public List<Permission> listByRoleId(int roleId) {
        return mapper.getByRoleId(roleId);
    }

    public List<Permission> listByUserId(Integer userId) {
        return mapper.listByUserId(userId);
    }

    public List<Permission> listAuthc() {
        return mapper.listAuthc();
    }

    public List<PermissionDTO> listDto(int userId) {
        List<Permission> pnavs = mapper.listByParentIdAndUserId(0, userId);
        List<PermissionDTO> dnavs = new ArrayList<>();
        for (Permission pnav : pnavs) {
            PermissionDTO dnav = new PermissionDTO();
            BeanUtils.copyProperties(pnav, dnav, "children");
            List<Permission> menus = mapper.listByParentIdAndUserId(pnav.getId(), userId);
            List<PermissionDTO> dmenus = new ArrayList<>();
            for (Permission menu : menus) {
                PermissionDTO dmenu = new PermissionDTO();
                BeanUtils.copyProperties(menu, dmenu, "children");
                List<Permission> items = mapper.listByParentIdAndUserId(menu.getId(), userId);
                List<PermissionDTO> ditems = new ArrayList<>();
                for (Permission item : items) {
                    PermissionDTO ditem = new PermissionDTO();
                    BeanUtils.copyProperties(item, ditem, "children");
                    ditems.add(ditem);
                }
                dmenu.setChildren(ditems);
                dmenus.add(dmenu);
            }
            dnav.setChildren(dmenus);
            dnavs.add(dnav);
        }
        return dnavs;
    }
}
