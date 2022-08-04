package com.bhw.bhwtechology.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.bhwtechology.mapper.TechologyInfoMapper;
import com.bhw.bhwtechology.pojo.TechologyInfo;
import com.bhw.bhwtechology.servicce.ReleaseMgrService;
import com.bhw.bhwtechology.vo.ServerBussinessVo;
import com.bhw.common.base.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/releaseMgr")
public class ReleaseMgrRest {
    @Autowired
    ReleaseMgrService releaseMgrService;

    @Resource
    TechologyInfoMapper techologyInfoMapper;

    @RequestMapping(value = "/getBussinessType",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getBussinessType(Integer type){
        Response<Object> response = new Response<>();
        List<ServerBussinessVo> list= releaseMgrService.getBussinessType(type);
        response.setData(list);
        return response;
    }
    @RequestMapping(value = "/releaseServer")
    @ResponseBody
    public Response<Object> releaseTaxServer(TechologyInfo req){
        Response<Object> response = new Response<>();
        req.setCreateTime(new Date());
        if(StringUtils.isNotEmpty(req.getUId())){
            req.setUserId(Integer.valueOf(req.getUId()));
        }
        req.setCreateTime(new Date());
        req.setAuditStatus(0);//未审核
        techologyInfoMapper.insert(req);
        return response;
    }

    @RequestMapping(value = "/delTechologyInfo")
    @ResponseBody
    public Response<Object> delTechologyInfo(String ids){
        Response<Object> response = new Response<>();
        techologyInfoMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }

    @RequestMapping(value = "/upTechologyInfo")
    @ResponseBody
    public Response<Object> upTechologyInfo(TechologyInfo techologyInfo){
        Response<Object> response = new Response<>();
        techologyInfoMapper.updateById(techologyInfo);
        return response;
    }

    @RequestMapping(value = "/getTechologyInfo")
    @ResponseBody
    public Response<Object> getTechologyInfo(TechologyInfo techologyInfo){
        Response<Object> response = new Response<>();
        TechologyInfo techologyInfo1 = techologyInfoMapper.selectOne(new QueryWrapper<>(techologyInfo));
        response.setData(techologyInfo1);
        return response;
    }
}
