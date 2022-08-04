package com.bhw.admin.rest;

import com.bhw.admin.mapper.CollegeMapper;
import com.bhw.admin.pojo.SchoolBean;
import com.bhw.admin.service.CollegeService;
import com.bhw.admin.vo.CollegesVo;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/college")
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @Resource
    CollegeMapper collegeMapper;

    /**
     * 按类型统计高校数
     * @param type 类型（0国外，1国内,不传查所有）
     * @return
     */
    @RequestMapping(value = "/getCollegeCount",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getCollegeCount(@RequestParam(value = "type",required = false) Integer type){
        Response<Object> response = new Response<>();
        int count=collegeService.getCollegeCount(type);
        response.setData(count);
        return response;
    }

    /**
     * 按类型查询高校
     * @param type 类型（0国外，1国内）
     * @return
     */
    @RequestMapping(value = "/getCollege",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getCollege(@RequestParam(value = "type",required = true) Integer type,
                                       Page page){
        Response<Object> response = new Response<>();
        List<CollegesVo> list=collegeService.getColleges(type, page);
        response.setData(list);
        response.setPage(page);
        return response;
    }


    @RequestMapping(value = "/qryCollegeList")
    @ResponseBody
    public Response<Object> qryCollegeList(SchoolBean schoolBean,Page page){
        Response<Object> response = new Response<>();
        List<SchoolBean> list=collegeService.qryCollegeList(schoolBean, page);
        response.setData(list);
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/saveSchoolInfo")
    @ResponseBody
    public Response<Object> saveSchoolInfo(SchoolBean schoolBean){
        Response<Object> response = new Response<>();
        collegeMapper.insert(schoolBean);
        response.setData(schoolBean.getId());
        return response;
    }

    @RequestMapping(value = "/upSchoolInfo")
    @ResponseBody
    public Response<Object> upSchoolInfo(SchoolBean schoolBean){
        Response<Object> response = new Response<>();
        collegeMapper.updateById(schoolBean);
        return response;
    }

    @RequestMapping(value = "/delSchoolInfo")
    @ResponseBody
    public Response<Object> saveSchoolInfo(String idList){
        Response<Object> response = new Response<>();
        collegeMapper.deleteBatchIds(Arrays.asList(idList.split(",")));
        return response;
    }
}
