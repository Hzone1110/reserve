package com.reserve.mapper;

import com.reserve.pojo.Info;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoMapper {
    void addInfo(Info info);

    void deleteInfo(int rsvID);

    List<Info> getInfoBySes(int sesID);

    List<Info> getInfoByEmail(String email);
}
