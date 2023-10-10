package com.lgl.qidian.controller;

import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther 刘广林
 */
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody User user, HttpServletResponse response) throws IOException {
        System.out.println(user);

        userService.service(user, response);
    }
}
