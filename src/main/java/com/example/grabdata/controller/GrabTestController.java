package com.example.grabdata.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.grabdata.mapper.IGrabData;
import com.example.grabdata.model.*;
import com.example.grabdata.service.GrabDataService;
import com.example.grabdata.service.GrabDataServiceImpl;
import com.example.grabdata.util.MyDateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2020/5/28 17:06
 */
@RestController
@RequestMapping("/test")
public class GrabTestController {

    public static final String CHROME_DRIVER = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe";

    public static final String[] COURSES = {"语文","数学","英语","物理"};

    @Value("${domain.name}")
    public String DOMAIN_NAME;


    @Autowired
    private IGrabData grabData;

    public WebDriver getWebDriver(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.merge(capabilities);
        WebDriver webDriver = new ChromeDriver(options);
        //webDriver.manage().timeouts().implicitlyWait(65, TimeUnit.MINUTES);
        return webDriver;
    }

    //@Scheduled(cron = "30 45 0 * * ?")
    @GetMapping("/findData")
    public String findData() throws Exception{
        System.out.println("********************** 开始执行时间:"+MyDateTimeUtils.format(LocalDateTime.now()) +" **********************");
        Long start = System.currentTimeMillis();
        System.getProperties().setProperty("webdriver.chrome.driver",CHROME_DRIVER);
        WebDriver webDriver = getWebDriver();
        webDriver.get(DOMAIN_NAME);
        List<WebElement> elements = webDriver.findElements(By.xpath("//ul[@class='subject-list']/li"));
        for (int i = 0; i < elements.size(); i++) {
            WebElement webElement = elements.get(i);
            if("物理".equals(webElement.getText())){
                System.out.println("------ 开始抓取课程【"+webElement.getText()+"】数据 ------");
                webDriver = getWebDriver();
                grab(webDriver,webElement);
                System.out.print("------ 完成抓取课程【"+webElement.getText()+"】数据 ");
                System.out.println(",耗时："+(System.currentTimeMillis()-start)+" ms ------");
            }
        }
        webDriver.close();
        System.out.println("********************** 完成执行时间:"+MyDateTimeUtils.format(LocalDateTime.now())
                +",耗时："+(System.currentTimeMillis()-start)+" ms **********************");
        return "success,耗时 : "+(System.currentTimeMillis()-start)+" ms";
    }


    public void grab(WebDriver webDriver, WebElement element){
        String attribute = element.findElement(By.tagName("a")).getAttribute("href");
        String courseId = element.findElement(By.tagName("a")).getAttribute("data-value");
        String courseName = element.findElement(By.tagName("a")).getText();
        //获取系统课的信息
        webDriver.get(DOMAIN_NAME+attribute.substring(attribute.indexOf("subject")));
        long sub = System.currentTimeMillis();
        List<WebElement> systemLists = webDriver.findElements(By.xpath("//ul[@class='sub-system-list']/li"));
        System.out.println("耗时："+(System.currentTimeMillis()-sub));
        System.out.println("获取系统课的信息："+systemLists.size()+"条");
        if(systemLists.size()>0){
            List<CourseList> list = new ArrayList<>();
            for (int i = 0; i < systemLists.size(); i++) {
                //保存CourseList表
                WebElement li = systemLists.get(i);
                String[] courses = li.getText().split("\n");
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                String cousePackId = json.getString("course_pack_id");
                CourseList vo = new CourseList(courseId,null
                        ,String.valueOf(1),cousePackId,courses[0]
                        ,courses[1],courses[2],courses[3],LocalDateTime.now(),LocalDateTime.now());
                list.add(vo);

                webDriver = getWebDriver();
                grabSystemDetail(webDriver,DOMAIN_NAME+"subject/"+courseId+"/subject_system/"+cousePackId);

            }
            grabData.insertCourseList(list);
            System.out.println("CourseList表插入系统课 "+list.size()+" 条完毕");
        }

        //获取专题课的信息
        webDriver.get(DOMAIN_NAME+attribute.substring(attribute.indexOf("subject")));
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='sub-subject-list']/li"));
        System.out.println("获取专题课的信息："+subjectLists.size()+"条");
        if(subjectLists.size()>0){
            List<CourseTeacherMapping> list = new ArrayList<>();
            List<CourseList> courseLists = new ArrayList<>();
            for (int i = 0; i < subjectLists.size(); i++) {
                WebElement li = subjectLists.get(i);
                String[] courses = li.getText().split("\n");
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                String courseChildId = json.getString("course_id");

                CourseList courseListVO = new CourseList(courseId,courseChildId
                        ,String.valueOf(2),null,courses[0]
                        ,courses[1],courses[4],courses[5],LocalDateTime.now(),LocalDateTime.now());
                courseLists.add(courseListVO);
                CourseTeacherMapping vo = new CourseTeacherMapping(null,courseChildId
                        ,courses[0],courses[1],StringUtils.join(Arrays.copyOfRange(courses,2,courses.length-2))
                        ,null,null,courses[courses.length-1],LocalDateTime.now(),LocalDateTime.now());
                list.add(vo);
                webDriver = getWebDriver();
                grabTeacherDetail(webDriver,DOMAIN_NAME+"pc/course.html?course_id="+courseChildId);
            }
            grabData.insertCourseList(courseLists);
            System.out.println("CourseList表插入专题课 "+courseLists.size()+" 条完毕");
            grabData.insertCourseTeacherMapping(list);
            System.out.println("专题课CourseTeacherMapping表插入 "+list.size()+" 条完毕");
        }

        //插入统计表
        GrabDashboard vo = new GrabDashboard(courseId,courseName
                ,systemLists.size()+subjectLists.size()
                ,LocalDateTime.now(),LocalDateTime.now());
        grabData.insertDashboard(Arrays.asList(vo));
        System.out.println("course_dashboard表插入数据完毕");
    }

    /**
     * 获取系统课的详情
     * @param webDriver
     * @param url
     */
    public void grabSystemDetail(WebDriver webDriver,String url){
        webDriver.get(url);
        List<WebElement> courseLists = webDriver.findElements(By.xpath("//div[@class='sys-pkg-ct']/li"));
        if(courseLists.size()>0){
            List<CourseTeacherMapping> list = new ArrayList<>();
            for (int i = 0; i < courseLists.size(); i++) {
                WebElement li = courseLists.get(i);
                String[] courses = li.getText().split("\n");
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                String courseId = json.getString("course_id");

                CourseTeacherMapping vo = new CourseTeacherMapping(url.substring(url.lastIndexOf("/")+1)
                        ,courseId,courses[0],courses[1],StringUtils.join(Arrays.copyOfRange(courses,2,courses.length-2))
                        ,null,courses[courses.length-2],courses[courses.length-1],LocalDateTime.now(),LocalDateTime.now());
                list.add(vo);
                webDriver = getWebDriver();
                grabTeacherDetail(webDriver,DOMAIN_NAME+"pc/course.html?course_id="+courseId);
            }
            grabData.insertCourseTeacherMapping(list);
            System.out.println("系统课CourseTeacherMapping表插入 "+list.size()+" 条完毕");
        }
    }

    /**
     * 获取专题课的详情
     * @param webDriver
     * @param url
     */
    public void grabTeacherDetail(WebDriver webDriver, String url){
        webDriver.get(url);
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='teacherList']/li"));
        if(subjectLists.size()>0){
            List<CourseTeacherInfo> list = new ArrayList<>();
            for (int i = 0; i < subjectLists.size(); i++) {
                WebElement li = subjectLists.get(i);
                String[] split = li.getText().split("\n");
                CourseTeacherInfo vo = new CourseTeacherInfo(url.substring(url.indexOf("=")+1)
                        ,split[0],null,null,LocalDateTime.now(),LocalDateTime.now());
                if(split.length>2){
                    vo.setRate(split[1]);
                    vo.setNote(split[2]);
                }else {
                    vo.setNote(split[1]);
                }
                list.add(vo);
            }
            grabData.insertCourseTeacherInfo(list);
            System.out.println("CourseTeacherInfo表插入 "+list.size()+" 条完毕");
        }
    }

}
