package com.example.grabdata.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author admin
 * @date 2020/5/29 13:27
 */
@Data
public class CourseQueryParam {

    public CourseQueryParam(){}
    private String coursePackId;
    private String courseParentId;
    private String courseChildId;
    private String courseId;
    private String createTime;


}
