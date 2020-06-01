package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 教师信息vo
 * @author admin
 * @date 2020/5/28 16:20
 */
@Data
@AllArgsConstructor
public class CourseTeacherInfo {

    public CourseTeacherInfo(){}
    private String courseId;
    private String teacherName;
    private String rate;
    private String note;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
