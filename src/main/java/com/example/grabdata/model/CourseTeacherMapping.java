package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程和教师映射vo
 * @author admin
 * @date 2020/5/28 16:18
 */
@Data
@AllArgsConstructor
public class CourseTeacherMapping {

    public CourseTeacherMapping(){}
    private String coursePackId;
    private String courseId;
    private String className;
    private String classDuration;
    private String teacherName;
    private String jobType;
    private String remainNums;
    private String classPrice;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
