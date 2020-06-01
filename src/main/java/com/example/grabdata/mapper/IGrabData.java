package com.example.grabdata.mapper;

import com.example.grabdata.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2020/5/28 11:50
 */
@Repository
public interface IGrabData {

    public int insertDashboard(List<GrabDashboard> list);

    public int insertCourseList(List<CourseList> list);

    public int insertCourseTeacherMapping(List<CourseTeacherMapping> list);

    public int insertCourseTeacherInfo(List<CourseTeacherInfo> list);

    public List<GrabDashboard> queryDashboard(CourseQueryParam vo);

    public List<CourseList> queryCourseList(CourseQueryParam vo);

    public List<CourseTeacherMapping> queryCourseTeacherMapping(CourseQueryParam vo);

    public List<CourseTeacherInfo> queryCourseTeacherInfo(CourseQueryParam vo);

}
