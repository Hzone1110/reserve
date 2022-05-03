package com.reserve;

import com.reserve.mapper.InfoMapper;
import com.reserve.mapper.SesMapper;
import com.reserve.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
