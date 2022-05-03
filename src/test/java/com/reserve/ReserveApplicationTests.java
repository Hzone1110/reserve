package com.reserve;

import com.reserve.mapper.InfoMapper;
import com.reserve.mapper.SesMapper;
import com.reserve.mapper.UserMapper;
import com.reserve.pojo.Info;
import com.reserve.pojo.Ses;
import com.reserve.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ReserveApplicationTests {
    @Autowired
    InfoMapper infoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SesMapper sesMapper;

    @Test
    void contextLoads() {
    }

}
