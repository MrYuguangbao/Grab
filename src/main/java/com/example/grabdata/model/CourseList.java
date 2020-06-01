package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 课程信息vo
 * @author admin
 * @date 2020/5/28 14:53
 */
@Data
@AllArgsConstructor
public class CourseList {

    public CourseList(){}

    private String courseParentId;
    private String courseChildId;
    private String courseType;
    private String coursePackId;
    private String courseName;
    /**
     * 课程起止时间
     */
    private String courseDuration;
    /**
     * 课程报名情况
     */
    private String courseEnlist;
    private String coursePrice;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
