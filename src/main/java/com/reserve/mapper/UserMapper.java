package com.reserve.mapper;

import com.reserve.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();

    User queryUserByEmail(String email);

    User queryUserById(Integer id);

    int addUser(String email);
}
