package com.reserve.controller;

import com.reserve.mapper.AdminMapper;
import com.reserve.mapper.UserMapper;
import com.reserve.pojo.Admin;
import com.reserve.pojo.User;
import com.reserve.service.EmailService;
import com.reserve.util.TokenUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@Tag(name = "LoginController")
public class LoginController {
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Autowired
    public LoginController(AdminMapper adminMapper, UserMapper userMapper, EmailService emailService) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }


    @PostMapping("/api/adminLogin")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Map<String, String> map) {
        String account = map.get("account");
        String password = map.get("password");
        Map<String, String> m = new HashMap<>();
        Admin admin = adminMapper.queryAdminByAccount(account);
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                String token = TokenUtil.generateToken(admin.getAccount());
                m.put("msg", "success");
                m.put("token", token);
            } else {
                m.put("msg", "密码错误");
            }
        } else {
            m.put("msg", "账号不存在");
        }
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @PostMapping("/api/userLogin")
    public ResponseEntity<Map<String, String>> userLogin(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        Map<String, String> m = new HashMap<>();
        if (Pattern.matches("^20\\d{6}@stu\\.neu\\.edu\\.cn$", email)) {
            User user = userMapper.queryUserByEmail(email);
            if (user == null) {
                userMapper.addUser(email);
                user = userMapper.queryUserByEmail(email);
            }
            m.put("msg", "success");
            String token = TokenUtil.generateToken(user.getEmail());
            emailService.sendHtmlMail(email, "预约维修登录链接", "<a href=\"http://localhost:8080/user/auth?token= + " + token + "\" target=\"_blank\">预约维修登录</a>");
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        m.put("msg", "邮箱错误");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @GetMapping("/api/userAuth")
    public ResponseEntity<Map<String, String>> userAuth(HttpServletRequest request) {
        String token = TokenUtil.getToken(request);
        Map<String, String> m = TokenUtil.parseToken(token);
        User user = userMapper.queryUserByEmail(m.get("account"));
        m.clear();
        m.put("id", String.valueOf(user.getId()));
        m.put("email", user.getEmail());
        return new ResponseEntity<>(m, HttpStatus.OK);
    }
}

