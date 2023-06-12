package com.reserve.mapper;

import com.reserve.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin queryAdminByAccount(String account);
}
