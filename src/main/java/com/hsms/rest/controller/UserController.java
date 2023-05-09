package com.hsms.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsms.mybatis.model.User;
import com.hsms.res.DefaultRes;
import com.hsms.rest.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<DefaultRes<List<User>>> findAll() {
        return new ResponseEntity<DefaultRes<List<User>>>(userService.findAll(), HttpStatus.OK);
    }
}
