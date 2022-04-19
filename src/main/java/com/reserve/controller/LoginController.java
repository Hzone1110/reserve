package com.reserve.controller;

import com.reserve.service.EmailService;
import com.reserve.mapper.AdminMapper;
import com.reserve.mapper.UserMapper;
import com.reserve.pojo.Admin;
import com.reserve.pojo.User;
import com.reserve.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class LoginController {
    private final TokenUtil tokenUtil;
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    public LoginController(AdminMapper adminMapper, UserMapper userMapper, EmailService emailService, TokenUtil tokenUtil) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.tokenUtil = tokenUtil;
    }


    @PostMapping("/api/adminLogin")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Map<String, String> map) {
        String account = map.get("account");
        String password = map.get("password");
        Map<String, String> m = new HashMap<>();
        Admin admin = adminMapper.queryAdminByAccount(account);
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                m.put("msg", "success");
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
            String token = tokenUtil.generateToken(user.getId(), user.getEmail());
            emailService.sendSimpleMail(email, "登录链接", "localhost:8080/user/auth?token=" + token);
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        m.put("msg", "邮箱错误");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @GetMapping("/api/userAuth")
    public ResponseEntity<Map<String, String>> userAuth(HttpServletRequest request) {
        String token = tokenUtil.getToken(request);
        Map<String, String> m = tokenUtil.parseToken(token);
        m.remove("timeStamp");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }
}

