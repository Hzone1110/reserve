package com.reserve;

import com.reserve.mapper.InfoMapper;
import com.reserve.mapper.UserMapper;
import com.reserve.pojo.Info;
import com.reserve.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class ReserveApplicationTests {
    @Autowired
    InfoMapper infoMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() throws SQLException {
        String email = "20206787@stu.neu.edu.cn";
        List<Info> infos = infoMapper.getInfoBySes(1);
        System.out.println(infos);
    }

}
