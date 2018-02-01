package com.d.dao;

import org.springframework.data.repository.CrudRepository;

import com.d.entity.UserDto;

/**
 * @author di
 */
public interface UserDao extends CrudRepository<UserDto, Integer> {

}
