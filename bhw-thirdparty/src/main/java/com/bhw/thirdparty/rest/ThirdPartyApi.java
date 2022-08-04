package com.bhw.thirdparty.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bhw.thirdparty.pojo.*;
import com.bhw.thirdparty.service.UserService;
import com.bhw.thirdparty.vo.DataVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RefreshScope
@RequestMapping("/thirdPartyApi")
public class ThirdPartyApi {

    Logger logger = LoggerFactory.getLogger(ThirdPartyApi.class);

    @Autowired
    UserService userService;

    @Autowired
    private Environment env;

    @Value("${test}")
    public String port;

    /**
     * 获取经纪人列表
     * */
    @RequestMapping(value="/getBrokerList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBrokerList(HttpServletResponse response){
        UserBean userBean = new UserBean();
        userBean.setType(2);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(userService.qryUserList(userBean));
    }

    /**
     * 获取专家列表
     * */
    @RequestMapping(value="/getExpertList",method = RequestMethod.GET)
    public String getExpertList(HttpServletResponse response){
        UserBean userBean = new UserBean();
        userBean.setType(3);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(userService.qryUserList(userBean));
    }

    /**
     * 获取科技成果项目列表
     * */
    @RequestMapping(value="/getProjectSummary",method = RequestMethod.GET)
    public String getProjectSummary(HttpServletResponse response){
        ProjectBean projectBean = new ProjectBean();
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(userService.qryProjectList(projectBean));
    }


    /**
     * 获取高校校徽
     * */
    @RequestMapping(value="/getSchoolBadge",method = RequestMethod.GET)
    public String getSchoolBadge(HttpServletResponse response){
        SchoolBean schoolBean = new SchoolBean();
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(userService.qrySchoolList(schoolBean));
    }

    /**
     * 获取成果
     * */
    @RequestMapping(value="/getOutComeList",method = RequestMethod.GET)
    public String getOutComeList(OutComeBean outComeBean,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(userService.qryOutComeList(outComeBean));
    }

    /**
     * 获取金融机构
     * */
    @RequestMapping(value="/getOrganizationList",method = RequestMethod.GET)
    public String getOrganizationList(OrganizationBean organizationBean,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSON.toJSONString(userService.qryOrganizationList(organizationBean));
    }


    @RequestMapping(value="/qryDataByParam",method = RequestMethod.GET)
    public String qryDataByParam(String param,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","专家/经纪人");
        jsonObject.put("list",userService.qryUserListByTypeName(param));
        jsonArray.add(jsonObject);

        JSONObject jsonObject2 = new JSONObject();
        SchoolBean schoolBean = new SchoolBean();
        schoolBean.setName(param);
        jsonObject2.put("name","合作高校/院所");
        List<SchoolBean> slist = userService.qrySchoolList(schoolBean);
        List<DataVo> dataVoList = new ArrayList<>();
        for (SchoolBean schoolBean1:slist){
            DataVo dataVo = new DataVo();
            dataVo.setTitle(schoolBean1.getName());
            dataVo.setAvatar(schoolBean1.getPhoto());
            dataVo.setUrl(schoolBean1.getUrl());
            dataVoList.add(dataVo);
        }
        jsonObject2.put("list",dataVoList);
        jsonArray.add(jsonObject2);

        JSONObject jsonObject3 = new JSONObject();
        PatentBean patentBean = new PatentBean();
        patentBean.setPatentname(param);
        jsonObject3.put("name","入库成果");
        List<PatentBean> plist = userService.qryPatentList(patentBean);
        dataVoList = new ArrayList<>();
        for (PatentBean patentBean1:plist){
            DataVo dataVo = new DataVo();
            dataVo.setTitle(patentBean1.getPatentname());
            dataVo.setAvatar("http://yhtg.huayunstar.com:8001/jishu/image/"+patentBean1.getPatentcode()+".gif");
            dataVo.setSummary(patentBean1.getContent());
            dataVoList.add(dataVo);
        }
        jsonObject3.put("list",dataVoList);
        jsonArray.add(jsonObject3);

        JSONObject jsonObject4 = new JSONObject();
        OrganizationBean organizationBean = new OrganizationBean();
        organizationBean.setType(param);
        jsonObject4.put("name","金融机构");
        List<OrganizationBean> olist = userService.qryOrganizationList(organizationBean);
        dataVoList = new ArrayList<>();
        for (OrganizationBean organizationBean1:olist){
            DataVo dataVo = new DataVo();
            dataVo.setTitle(organizationBean1.getName());
            dataVo.setAvatar(organizationBean1.getImage());
            dataVo.setSummary(organizationBean1.getType());
            dataVoList.add(dataVo);
        }
        jsonObject4.put("list",dataVoList);
        jsonArray.add(jsonObject4);

        return jsonArray.toJSONString();
    }

    @RequestMapping("/test")
    public String test(){
        List<UserBean> list =  userService.test();
        logger.info("|数据库User|"+ JSON.toJSONString(list));
        return "test|"+env.getProperty("test")+"|"+port;
    }
}
