package com.reserve.mapper;

import com.reserve.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User queryUserByEmail(String email);

    void addUser(String email);
}
