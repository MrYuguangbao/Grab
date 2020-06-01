package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 * @date 2020/5/29 13:41
 */
@Data
public class CourseTeacherQueryResult {

    public CourseTeacherQueryResult(){}
    private String coursePackId;
    private String courseId;
    private String className;
    private String classDuration;
    private String jobType;
    private String remainNums;
    private String classPrice;
    private List<CourseTeacherInfo> teacherInfos;

}
