package com.d.service;

import com.d.base.AbstractService;
import com.d.entity.User;

/**
 * @author d
 */
public interface UserService extends AbstractService<User, UserService> {
    User getByUsername(String username);

    Integer saveUser(User u, Integer[] roleIds);
}
