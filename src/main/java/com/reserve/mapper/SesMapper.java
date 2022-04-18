package com.reserve.mapper;

import com.reserve.pojo.Ses;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;

@Mapper
@Repository
public interface SesMapper {
    int addSes(Ses ses);

    int deleteSes(int id);

    int putSes(Ses ses);

    Ses getSes(int id);

    List<Ses> getSome(Data fromDate);

}
