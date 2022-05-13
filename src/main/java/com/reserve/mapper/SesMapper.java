package com.reserve.mapper;

import com.reserve.pojo.Ses;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface SesMapper {
    void addSes(Ses ses);

    void deleteSes(int sesID);

    void deleteInfo(int sesID);

    Ses getSes(int sesID);

    List<Ses> getSome(Date fromDate);

    List<Ses> getAll();

}
