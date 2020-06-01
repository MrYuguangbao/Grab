package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import sun.util.resources.LocaleData;

import java.time.LocalDateTime;

/**
 * 课程详情
 * @author admin
 * @date 2020/5/28 12:14
 */
@Data
@AllArgsConstructor
public class CourseDetail {

    private String courseId;
    private String courseParentFlag;
    private String courseChildFlag;
    private String classId;
    private String classTitle;
    private String classSell;
    private String classTeacher;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    public CourseDetail(){}

}
