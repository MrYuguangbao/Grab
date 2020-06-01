package com.example.grabdata.util;

import com.alibaba.fastjson.JSONObject;
import com.example.grabdata.model.CourseList;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 系统课
 * @author admin
 * @date 2020/5/28 8:26
 */
public class GrabSystemList {


    public static final String DOMAIN_NAME = "https://fudao.qq.com/";
    public static final String CHROME_DRIVER = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe";

    public static WebDriver getWebDriver(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.merge(capabilities);
        WebDriver webDriver = new ChromeDriver(options);
        return webDriver;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        System.getProperties().setProperty("webdriver.chrome.driver",CHROME_DRIVER);

        WebDriver webDriver = getWebDriver();
        webDriver.get(DOMAIN_NAME+"subject/6005/");
        List<WebElement> systemLists = webDriver.findElements(By.xpath("//ul[@class='sub-system-list']/li"));
        if(systemLists.size()>0){
            for (int i = 0; i < 1; i++) {
                /*WebElement li = systemLists.get(i);
                String[] courses = li.getText().split("\n");
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                String cousePackId = json.getString("course_pack_id");

                //(course_parent_id,course_child_id,course_pack_id,course_type,course_name,course_duration,course_enlist,course_price,cTime,uTime)
                CourseList vo = new CourseList("6002",null
                        ,cousePackId,String.valueOf(1),courses[0]
                        ,courses[1],courses[2],courses[3],LocalDateTime.now(),LocalDateTime.now());
                grabData.insertCourseList(Arrays.asList(vo));*/


                webDriver = getWebDriver();
                grabSystemDetail(webDriver,DOMAIN_NAME+"subject/6005/subject_system/str_sys_course_pkg_info_1_6005_8_0");
            }
        }

        System.out.println("处理耗时："+(System.currentTimeMillis()-start)+" ms");
        webDriver.close();
    }

    public static void grabSystemDetail(WebDriver webDriver,String url){
        webDriver.get(url);
        List<WebElement> courseLists = webDriver.findElements(By.xpath("//div[@class='sys-pkg-ct']/li"));
        if(courseLists.size()>0){
            for (WebElement li : courseLists) {
                System.out.println("--------li------");
                System.out.println(li.getText());
                String[] courses = li.getText().split("\n");
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                System.out.println("课程id:"+json.getString("course_id"));

                System.out.println(StringUtils.join(Arrays.copyOfRange(courses,2,courses.length-2)));


                //存入课程与教师对应的信息表 TODO

                /*String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                System.out.println("课程id:"+json.getString("course_id"));
                webDriver = new ChromeDriver();
                grabTeacherDetail(webDriver,DOMAIN_NAME+"pc/course.html?course_id="+json.getString("course_id"));*/
            }
        }
    }

}
