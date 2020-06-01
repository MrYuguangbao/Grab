package com.example.grabdata.login;

import com.example.grabdata.model.CourseQueryParam;
import com.example.grabdata.model.GrabDashboard;
import com.example.grabdata.model.UserVO;
import com.example.grabdata.service.GrabDataServiceImpl;
import com.example.grabdata.service.UserService;
import com.example.grabdata.util.MyDateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author admin
 * @date 2019/6/7 14:48
 */
@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${hrefPath}")
    private String hrefPath;

    @Autowired
    private UserService userService;

    @Autowired
    private GrabDataServiceImpl grabDataService;

    @RequestMapping(value = "/")
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/toIndex")
    public String toIndex(HttpServletRequest request) {
        String url = request.getParameter("url");
        logger.info("---------- url:" + url);
        return url;
    }

    /**
     * 通过bean接收
     * @param userVO
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UserVO userVO, Model model) {
        UserVO result = userService.login(userVO.getUsername(), userVO.getPassword());
        model.addAttribute("login", result);
        if (result != null) {
            //默认查今天的数据
            /*CourseQueryParam vo = new CourseQueryParam();
            String queryTime = MyDateTimeUtils.getMinusDay(Integer.parseInt("0"));
            vo.setCreateTime(queryTime);
            List<GrabDashboard> results = grabDataService.queryDashboard(vo);
            model.addAttribute("results", results);*/
            return "dashboard-data";
        }else {
            model.addAttribute("results", -1);
            return "login";
        }
    }

}
