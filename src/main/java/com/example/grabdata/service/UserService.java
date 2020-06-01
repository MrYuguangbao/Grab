package com.example.grabdata.service;

import com.example.grabdata.model.UserVO;

/**
 * @author admin
 * @date 2020/5/29 15:35
 */
public interface UserService {
    UserVO login(String username, String password);
}
