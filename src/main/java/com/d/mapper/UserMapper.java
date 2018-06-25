package com.d.mapper;

import com.d.base.BaseMapper;
import com.d.entity.User;
import com.d.enums.UserSexEnum;
import com.di.kit.SqlProvider;

import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> getAll();

    @SelectProvider(type = SqlProvider.class, method = "get")
    @Results({@Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class)})
    User getOne(User u);

    User getById(long id);

    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    List<User> query(@Param("id") Integer id, @Param("ids") Collection<Integer> ids, @Param("start") Date start, @Param("end") Date end);

    int batchInsert(List<User> users);

    int batchUpdate(List<User> users);
}