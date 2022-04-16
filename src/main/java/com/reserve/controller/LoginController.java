package com.reserve.controller;

import com.reserve.service.EmailService;
import com.reserve.mapper.AdminMapper;
import com.reserve.mapper.UserMapper;
import com.reserve.pojo.Admin;
import com.reserve.pojo.User;
import com.reserve.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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
    public String adminLogin(String account, String password, Model model) {
        String msg;
        Admin admin = adminMapper.queryAdminByAccount(account);
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                msg = "success";
            } else {
                model.addAttribute("msg", "wrong password");
                msg = "wrong password";
            }
        } else {
            model.addAttribute("msg", "this account is not exist");
            msg = "this account is not exist";
        }
        return msg;
    }

    @PostMapping("/api/userLogin")
    public String userLogin(String email, Model model, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");

        String msg = null;
        if (Pattern.matches("^20[0-9]{6}@stu\\.neu\\.edu\\.cn$", email)) {
            User user = userMapper.queryUserByEmail(email);
            if (user == null) {
                userMapper.addUser(email);
                user = userMapper.queryUserByEmail(email);
            }
            String token = tokenUtil.generateToken(user.getId(), user.getEmail());
            emailService.sendSimpleMail(email, "登录链接", "网址" + token);
            msg = "success";
        }
        if (msg == null) {
            model.addAttribute("msg", "wrong email");
            msg = "wrong email";
        }
        return msg;
    }

    @PostMapping("/api/userAuth")
    public Map<String, String> userAuth(HttpServletRequest request) {
        String token = tokenUtil.getToken(request);
        return null;
    }
}
