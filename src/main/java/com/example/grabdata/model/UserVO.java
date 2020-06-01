package com.example.grabdata.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author admin
 * @date 2019/6/7 14:58
 */
@Data
public class UserVO {

    public UserVO(){}
    private Long roleId;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
