package com.example.grabdata.service;

import com.example.grabdata.model.*;

import java.util.List;

/**
 * @author admin
 * @date 2020/5/29 12:27
 */
public interface GrabDataService {
    public List<GrabDashboard> queryDashboard(CourseQueryParam vo);

    public List<CourseListQueryResult> queryCourseList(CourseQueryParam vo);

    public List<CourseTeacherMapping> queryCourseTeacherMapping(CourseQueryParam vo);

    public List<CourseTeacherInfo> queryCourseTeacherInfo(CourseTeacherInfo vo);

    public List<CourseTeacherQueryResult> queryClassInfo(CourseQueryParam vo);
}
