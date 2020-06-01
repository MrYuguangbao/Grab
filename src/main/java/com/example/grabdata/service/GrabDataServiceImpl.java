package com.example.grabdata.service;

import com.alibaba.fastjson.JSONObject;
import com.example.grabdata.mapper.IGrabData;
import com.example.grabdata.model.*;
import com.example.grabdata.util.MyDateTimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * @date 2020/5/28 11:55
 */
@Service
public class GrabDataServiceImpl implements GrabDataService {
    public static final String DOMAIN_NAME = "https://fudao.qq.com/";
    public static final String CHROME_DRIVER = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe";

    @Autowired
    private IGrabData grabData;

    @Override
    public List<GrabDashboard> queryDashboard(CourseQueryParam vo) {
        return grabData.queryDashboard(vo);
    }

    @Override
    public List<CourseListQueryResult> queryCourseList(CourseQueryParam vo) {
        List<CourseListQueryResult> results = new ArrayList<>();
        List<CourseList> list = grabData.queryCourseList(vo);
        //查询课程对应的班级详情
        for (CourseList courseList : list) {
            CourseQueryParam param = new CourseQueryParam();
            if("2".equals(courseList.getCourseType())){
                param.setCourseChildId(courseList.getCourseChildId());
            }else {
                param.setCoursePackId(courseList.getCoursePackId());
            }
            param.setCreateTime(vo.getCreateTime());
            List<CourseTeacherMapping> teacherMappings = grabData.queryCourseTeacherMapping(param);
            CourseListQueryResult course = new CourseListQueryResult();
            course.setClassNum(teacherMappings.size());

            BeanUtils.copyProperties(courseList,course);
            results.add(course);

        }
        for (CourseListQueryResult result : results) {
            String coursePrice = result.getCoursePrice();
            String str = coursePrice.substring(coursePrice.lastIndexOf("¥"));
            result.setCoursePrice(str);
        }
        return results;
    }

    /**
     * 根据course_pack_id和日期查询班级信息
     * @param vo
     * @return
     */
    @Override
    public List<CourseTeacherQueryResult> queryClassInfo(CourseQueryParam vo){
        List<CourseTeacherQueryResult> results = new ArrayList<>();
        List<CourseTeacherMapping> teacherMappings = grabData.queryCourseTeacherMapping(vo);
        if(teacherMappings.size()>0){
            for (CourseTeacherMapping mapping : teacherMappings) {
                CourseTeacherQueryResult queryResultVO = new CourseTeacherQueryResult();
                queryResultVO.setCourseId(mapping.getCourseId());
                queryResultVO.setClassDuration(mapping.getClassDuration());
                queryResultVO.setClassName(mapping.getClassName());
                String classPrice = mapping.getClassPrice();
                queryResultVO.setClassPrice(classPrice.substring(classPrice.lastIndexOf("¥")));
                queryResultVO.setCoursePackId(mapping.getCoursePackId());
                queryResultVO.setRemainNums(mapping.getRemainNums());
                //班级信息
                vo.setCourseId(mapping.getCourseId());
                queryResultVO.setTeacherInfos(grabData.queryCourseTeacherInfo(vo));
                results.add(queryResultVO);
            }
        }
        return results;
    }


    @Override
    public List<CourseTeacherMapping> queryCourseTeacherMapping(CourseQueryParam vo) {
        return grabData.queryCourseTeacherMapping(vo);
    }

    @Override
    public List<CourseTeacherInfo> queryCourseTeacherInfo(CourseTeacherInfo vo) {
        return null;
    }

}
