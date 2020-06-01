package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程数据总览
 * @author admin
 * @date 2020/5/28 11:47
 */
@Data
@AllArgsConstructor
public class GrabDashboard {

    public GrabDashboard(){}
    private String courseId;
    private String courseName;
    private Integer courseTotal;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
