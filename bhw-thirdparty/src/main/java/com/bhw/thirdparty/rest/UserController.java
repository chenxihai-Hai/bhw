package com.bhw.thirdparty.rest;

import com.bhw.thirdparty.pojo.*;
import com.bhw.thirdparty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/saveUser")
    public String saveUserInfo(UserBean userBean){
        return userService.saveUserInfo(userBean)+"";
    }


    @RequestMapping("/saveBasicsType")
    public String saveBasicsType(BasicsTypeBean basicsTypeBean){
        return userService.saveBasicsType(basicsTypeBean)+"";
    }

    @RequestMapping("/saveProject")
    public void saveProject(ProjectBean projectBean){userService.saveProject(projectBean);}

    @RequestMapping("/saveSchool")
    public void saveSchool(SchoolBean schoolBean){userService.saveSchool(schoolBean);}


    @RequestMapping("/savePatent")
    public void savePatent(PatentBean patentBean){userService.savePatent(patentBean);}

    @RequestMapping("/saveOutComeList")
    public void saveOutCome(List<OutComeBean> outComeList){userService.saveOutCome(outComeList);}

    @RequestMapping("/saveOutCome")
    public void saveOutCome(OutComeBean outCome){userService.saveOutCome(outCome);}

}
