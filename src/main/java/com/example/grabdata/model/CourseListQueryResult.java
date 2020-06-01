package com.example.grabdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author admin
 * @date 2020/5/29 13:33
 */
@Data
public class CourseListQueryResult extends CourseList {

    public CourseListQueryResult(){}
    private Integer classNum;


}
