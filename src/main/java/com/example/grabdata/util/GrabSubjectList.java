package com.example.grabdata.util;

import com.alibaba.fastjson.JSONObject;
import com.example.grabdata.model.CourseList;
import com.example.grabdata.model.CourseTeacherInfo;
import com.example.grabdata.model.CourseTeacherMapping;
import com.example.grabdata.model.GrabDashboard;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 专题课
 * @author admin
 * @date 2020/5/28 8:45
 */
public class GrabSubjectList {

    public static final String DOMAIN_NAME = "https://fudao.qq.com/";
    public static final String CHROME_DRIVER = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe";

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        System.getProperties().setProperty("webdriver.chrome.driver",CHROME_DRIVER);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.merge(capabilities);
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get(DOMAIN_NAME);
        List<WebElement> elements = webDriver.findElements(By.xpath("//ul[@class='subject-list']/li"));
        for (int i = 0; i < elements.size(); i++) {
            WebElement webElement = elements.get(i);
            if("物理".equals(webElement.getText())){
                System.out.println("------ 开始抓取课程【"+webElement.getText()+"】数据 ------");
                String attribute = webElement.findElement(By.tagName("a")).getAttribute("href");
                System.out.println(attribute);

                webDriver = new ChromeDriver(options);
                webDriver.get(DOMAIN_NAME+attribute.substring(attribute.indexOf("subject")));
                long s1 = System.currentTimeMillis();
                List<WebElement> systemLists = webDriver.findElements(By.xpath("//ul[@class='sub-system-list']/li"));
                System.out.println("获取系统课的信息："+systemLists.size()+"条,耗时："+(System.currentTimeMillis()-s1));

                webDriver.get(DOMAIN_NAME+attribute.substring(attribute.indexOf("subject")));
                long s2 = System.currentTimeMillis();
                List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='sub-subject-list']/li"));
                System.out.println("获取专题课的信息："+subjectLists.size()+"条,耗时："+(System.currentTimeMillis()-s2));


                System.out.print("------ 完成抓取课程【"+webElement.getText()+"】数据 ");
                System.out.println(",耗时："+(System.currentTimeMillis()-start)+" ms ------");
            }
        }


        /*webDriver.get(DOMAIN_NAME+"subject/6004/");
        List<WebElement> systemLists = webDriver.findElements(By.xpath("//ul[@class='sub-system-list']/li"));
        System.out.println(systemLists.size());
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='sub-subject-list']/li"));
        if(subjectLists.size()>0){
            List<CourseList> courseLists = new ArrayList<>();
            for (WebElement li : subjectLists) {
                System.out.println("------------专题课------------");
                System.out.println(li.getText());
                String[] courses = li.getText().split("\n");
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                String courseChildId = json.getString("course_id");
                System.out.println("courseChildId:"+courseChildId);
                CourseList courseListVO = new CourseList("6002",courseChildId
                        ,String.valueOf(2),null,courses[0]
                        ,courses[1],courses[2],courses[3],LocalDateTime.now(),LocalDateTime.now());
                courseLists.add(courseListVO);


                *//*String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);*//*
                //webDriver = new ChromeDriver();
                //grabSubjectTeacherDetail(webDriver,DOMAIN_NAME+"pc/course.html?course_id="+json.getString("course_id"));
            }
            System.out.println("专题课 : "+courseLists.size()+" 条");
        }*/

        System.out.println("处理耗时："+(System.currentTimeMillis()-start)+" ms");
    }

    public static void grabTeacherDetail(WebDriver webDriver, String url){
        System.out.println("---------teacher detail-----------");
        webDriver.get(url);
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='teacherList']/li"));
        if(subjectLists.size()>0){
            List<CourseTeacherInfo> list = new ArrayList<>();
            for (int i = 0; i < subjectLists.size(); i++) {
                WebElement li = subjectLists.get(i);
                System.out.println(li.getText());
            }
        }
    }

    public static void grabSubjectTeacherDetail(WebDriver webDriver,String url){
        webDriver.get(url);
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='teacherList']/li"));
        if(subjectLists.size()>0){
            for (WebElement li : subjectLists) {
                List<WebElement> divs = li.findElements(By.xpath("//div[@class='teacherContent']/div"));
                for (WebElement div : divs) {
                    System.out.println(div.getText());
                    List<WebElement> span = div.findElements(By.tagName("span"));
                    span.forEach(s-> System.out.println(s.getText()));
                }
            }
        }
        webDriver.close();
    }

}
