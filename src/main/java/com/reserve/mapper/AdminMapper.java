package com.reserve.mapper;

import com.reserve.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    Admin queryAdminByAccount(String account);
}
