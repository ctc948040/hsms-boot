package com.hsms.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hsms.ResponseMessage;
import com.hsms.mybatis.mapper.UserMapper;
import com.hsms.mybatis.model.User;
import com.hsms.res.DefaultRes;
import com.hsms.rest.StatusCode;

@Service
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 멤버 전체 조회
    public DefaultRes<List<User>> findAll() {
        final List<User> userList = userMapper.findAll();
        if (userList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, userList);
    }
}