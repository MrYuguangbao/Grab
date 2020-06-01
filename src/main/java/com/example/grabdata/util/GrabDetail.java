package com.example.grabdata.util;

import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * @date 2020/5/28 8:38
 */
public class GrabDetail {

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
        webDriver.get("https://fudao.qq.com/pc/course.html?course_id=204272");
        List<WebElement> subjectLists = webDriver.findElements(By.xpath("//ul[@class='teacherList']/li"));
        if(subjectLists.size()>0){
            for (WebElement li : subjectLists) {
                //存入教师信息表 TODO
                System.out.println("--------------li--------------");
                System.out.println(li.getText());

                /*String[] split = li.getText().split("\n");
                for (String s : split) {
                    System.out.println(s);
                }*/
            }
        }

        System.out.println("处理耗时："+(System.currentTimeMillis()-start)+" ms");
        webDriver.close();

    }

}
