package com.d.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d.base.BaseService;
import com.d.entity.User;
import com.d.entity.UserRole;
import com.d.mapper.UserMapper;
import com.d.mapper.UserRoleMepper;
import com.d.util.PasswordHelper;

/**
 * @author d
 */
@Service
public class UserService extends BaseService<UserMapper, User, UserService> {
    @Autowired
    private UserRoleMepper userRoleMepper;

    public User getByUsername(String username) {
        return mapper.getByUsername(username);
    }

    public Integer saveUser(User u, Integer[] roleIds) {
        int i = 0;
        if (u.isNewRecord()) {
            u.setPassword(u.getName());
            PasswordHelper.encryptPassword(u);
            i += mapper.insertSelective(u);
        } else {
            i += mapper.updateSelective(u);
        }
        List<UserRole> urs = userRoleMepper.listByUserId(u.getId());
        if (urs != null && !urs.isEmpty()) {
            for (UserRole ur : urs) {
                boolean exists = false;
                if (roleIds != null && roleIds.length > 0) {
                    for (Integer rid : roleIds) {
                        if (ur.getRoleId().equals(rid)) {
                            exists = true;
                            break;
                        }
                    }
                }
                if (!exists) {
                    userRoleMepper.delete(ur);
                }
            }
        }
        if (roleIds != null && roleIds.length > 0) {
            for (Integer rid : roleIds) {
                boolean needInsert = true;
                if (urs != null && !urs.isEmpty()) {
                    for (UserRole ur : urs) {
                        if (Objects.equals(ur.getRoleId(), rid)) {
                            needInsert = false;
                            break;
                        }
                    }
                }
                if (needInsert) {
                    UserRole ur = new UserRole();
                    ur.setRoleId(rid);
                    ur.setUserId(u.getId());
                    userRoleMepper.insertSelective(ur);
                }
            }
        }
        return i;
    }
}
