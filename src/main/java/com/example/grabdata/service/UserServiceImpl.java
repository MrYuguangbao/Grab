package com.example.grabdata.service;


import com.example.grabdata.mapper.GrabUserMapper;
import com.example.grabdata.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2019/6/7 15:27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GrabUserMapper userMapper;

    @Override
    public UserVO login(String username, String password) {
        return userMapper.login(username, password);
    }
}
