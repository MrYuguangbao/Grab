package com.example.grabdata.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.TODO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @date 2020/5/27 18:54
 */
public class Handler {

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
        System.out.println("课程id\t课程名称\t课程总数\t创建时间\t更新时间");
        for (int i = 0; i < 2; i++) {
            WebElement element = elements.get(i);
            if (!"全部".equals(element.getText())) {
                webDriver = new ChromeDriver();
                grabDashboard(webDriver,element);
            }
        }
        System.out.println("处理耗时："+(System.currentTimeMillis()-start)+" ms");

    }

    public static void grabDashboard(WebDriver webDriver,WebElement element){
        String attribute = element.findElement(By.tagName("a")).getAttribute("href");
        String courseId = element.findElement(By.tagName("a")).getAttribute("data-value");
        String courseName = element.findElement(By.tagName("a")).getText();
        webDriver.get(DOMAIN_NAME+attribute.substring(attribute.indexOf("subject")));
        //获取系统课的信息
        List<WebElement> systemLists = webDriver.findElements(By.xpath("//ul[@class='sub-system-list']/li"));
        if(systemLists.size()>0){
            for (WebElement li : systemLists) {
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                System.out.println("course_pack_id:"+json.getString("course_pack_id"));
                webDriver = new ChromeDriver();
                grabSystemDetail(webDriver,DOMAIN_NAME+"subject/"+courseId+"/subject_system/"+json.getString("course_pack_id"));
            }
        }
        //获取专题课的信息
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='sub-subject-list']/li"));
        /*if(subjectLists.size()>0){
            for (WebElement li : subjectLists) {
                System.out.println(li.getText());
                System.out.println();
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                webDriver = new ChromeDriver();
                grabTeacherDetail(webDriver,DOMAIN_NAME+"pc/course.html?course_id="+json.getString("course_id"));
            }
        }*/

        System.out.println(courseId+"\t"+courseName+"\t"
                +(systemLists.size()+subjectLists.size())+"\t"
                +LocalDateTime.now()+"\t"+LocalDateTime.now());
        //存入数据库 TODO
        webDriver.close();
    }

    /**
     * 获取系统课的详情
     * @param webDriver
     * @param url
     */
    public static void grabSystemDetail(WebDriver webDriver,String url){
        webDriver.get(url);
        List<WebElement> courseLists = webDriver.findElements(By.xpath("//div[@class='sys-pkg-ct']/li"));
        if(courseLists.size()>0){
            for (WebElement li : courseLists) {
                System.out.println(li.getText());
                System.out.println();
                String dataDtw = li.findElement(By.tagName("a")).getAttribute("data-tdw");
                JSONObject json = JSONObject.parseObject(dataDtw);
                System.out.println("课程id:"+json.getString("course_id"));
                webDriver = new ChromeDriver();
                grabTeacherDetail(webDriver,DOMAIN_NAME+"pc/course.html?course_id="+json.getString("course_id"));
            }
        }
    }

    /**
     * 获取专题课的详情
     * @param webDriver
     * @param url
     */
    public static void grabTeacherDetail(WebDriver webDriver,String url){
        webDriver.get(url);
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='teacherList']/li"));
        if(subjectLists.size()>0){
            for (WebElement li : subjectLists) {
                String[] split = li.getText().split("\n");
                System.out.println(Arrays.toString(split));
            }
        }
    }

}
