package com.example.grabdata.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.grabdata.mapper.IGrabData;
import com.example.grabdata.model.*;
import com.example.grabdata.service.GrabDataService;
import com.example.grabdata.service.GrabDataServiceImpl;
import com.example.grabdata.util.MyDateTimeUtils;
import org.apache.ibatis.annotations.Param;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * @date 2020/5/27 19:58
 */
@Controller
public class GrabDataController {

    @Autowired
    private GrabDataService grabDataService;

    /**
     * 根据日期查询过滤
     * @param createTime
     * @return
     */
    @ResponseBody
    @RequestMapping("/dashboard")
    public List<GrabDashboard> queryDashboard(@RequestParam(required = false) String createTime){
        String queryTime = MyDateTimeUtils.getMinusDay(Integer.parseInt(createTime));
        CourseQueryParam param = new CourseQueryParam();
        param.setCreateTime(queryTime);
        List<GrabDashboard> results = grabDataService.queryDashboard(param);
        return results;
    }

    /**
     * 根据courseId和日期查询具体课程
     * @param courseId
     * @param createTime
     * @param model
     * @return
     */
    @RequestMapping("/courselist")
    public String queryCourseList(@RequestParam("courseId") String courseId,
                                  @RequestParam("createTime") String createTime,Model model){
        CourseQueryParam param = new CourseQueryParam();
        param.setCourseParentId(courseId);
        param.setCreateTime(MyDateTimeUtils.getMinusHour(createTime));
        List<CourseListQueryResult> results = grabDataService.queryCourseList(param);
        model.addAttribute("results",results);
        return "course-list";
    }

    /**
     * 获取课程下面的班级信息
     * @param coursePackId
     * @param createTime
     * @param model
     * @return
     */
    @RequestMapping("/classinfo")
    public String queryClassInfo(@Param("coursePackId") String coursePackId,
                                 @Param("courseChildId") String courseChildId,
                                 @Param("createTime") String createTime,Model model){
        CourseQueryParam param = new CourseQueryParam();
        param.setCoursePackId(coursePackId);
        param.setCourseChildId(courseChildId);
        param.setCreateTime(MyDateTimeUtils.getMinusHour(createTime));
        List<CourseTeacherQueryResult> results = grabDataService.queryClassInfo(param);
        model.addAttribute("results",results);
        return "classinfo";
    }

}
