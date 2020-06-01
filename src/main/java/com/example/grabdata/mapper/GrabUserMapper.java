package com.example.grabdata.mapper;


import com.example.grabdata.model.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrabUserMapper {

    UserVO login(@Param("username") String username, @Param("password") String password);
}
