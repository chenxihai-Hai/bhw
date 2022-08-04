package com.bhw.techSupply.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.common.util.HttpUtils;
import com.bhw.techSupply.mapper.TechSupplyInfoMapper;
import com.bhw.techSupply.pojo.TechSupplyInfo;
import com.bhw.techSupply.service.ReleaseMgrService;
import com.bhw.techSupply.vo.ServerBussinessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RefreshScope
@RequestMapping("/releaseMgr")
public class ReleaseMgrRest {

    @Autowired
    ReleaseMgrService releaseMgrService;

    @Resource
    TechSupplyInfoMapper techSupplyInfoMapper;

    private Logger log= LoggerFactory.getLogger(ReleaseMgrRest.class);

    @RequestMapping(value = "/getBussinessType",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getBussinessType(Integer type){
        Response<Object> response = new Response<>();
        List<ServerBussinessVo> list= releaseMgrService.getBussinessType(type);
        response.setData(list);
        return response;
    }

    @RequestMapping(value = "/upTechSupplyInfo",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> upTechSupplyInfo(TechSupplyInfo techSupplyInfo) {
        Response<Object> response = new Response<>();
        techSupplyInfoMapper.updateById(techSupplyInfo);
        return response;
    }

    @RequestMapping(value = "/releaseServer",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> releaseTaxServer(TechSupplyInfo techSupplyInfo){
        Response<Object> response = new Response<>();
        techSupplyInfo.setAdditStatus(0);//未审核
        techSupplyInfo.setCreateTime(new Date());
        techSupplyInfoMapper.insert(techSupplyInfo);
        return response;
    }

    @RequestMapping(value = "/getTechSupplyInfo",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getTechSupplyInfo(TechSupplyInfo techSupplyInfo){
        Response<Object> response = new Response<>();
        TechSupplyInfo techSupplyInfo1 = techSupplyInfoMapper.selectOne(new QueryWrapper<>(techSupplyInfo));
        response.setData(techSupplyInfo1);
        return response;
    }

    @RequestMapping(value = "/delTechSupplyInfo")
    @ResponseBody
    public Response<Object> delTechSupplyInfo(String ids){
        Response<Object> response = new Response<>();
        techSupplyInfoMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }

}
