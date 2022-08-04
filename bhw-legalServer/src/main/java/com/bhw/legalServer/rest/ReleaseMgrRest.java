package com.bhw.legalServer.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.legalServer.dto.ReleaseReq;
import com.bhw.legalServer.mapper.LegalServerInfoMapper;
import com.bhw.legalServer.pojo.LegalServerInfo;
import com.bhw.legalServer.service.ReleaseMgrService;
import com.bhw.legalServer.vo.ServerBussinessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/releaseMgr")
public class ReleaseMgrRest {

    @Autowired
    ReleaseMgrService releaseMgrService;

    @Resource
    LegalServerInfoMapper legalServerInfoMapper;

    @RequestMapping(value = "/getBussinessType",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getBussinessType(Integer type){
        Response<Object> response = new Response<>();
        List<ServerBussinessVo> list= releaseMgrService.getBussinessType(type);
        response.setData(list);
        return response;
    }

    @RequestMapping(value = "/releaseServer",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> releaseTaxServer(ReleaseReq req){
        Response<Object> response = new Response<>();
        req.setAuditStatus(0);
        boolean flag=releaseMgrService.saveReleaseInfo(req);
        response.setData(flag);
        return response;
    }

    @RequestMapping(value = "/upLegalServerInfo",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> upLegalServerInfo(LegalServerInfo req){
        Response<Object> response = new Response<>();
        legalServerInfoMapper.updateById(req);
        return response;
    }

    @RequestMapping(value = "/getLegalServerInfo",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> getLegalServerInfo(LegalServerInfo req){
        Response<Object> response = new Response<>();
        LegalServerInfo legalServerInfo = legalServerInfoMapper.selectOne(new QueryWrapper<>(req));
        response.setData(legalServerInfo);
        return response;
    }

    @RequestMapping(value = "/delLegalServerInfo")
    @ResponseBody
    public Response<Object> delLegalServerInfo(String ids){
        Response<Object> response = new Response<>();
        legalServerInfoMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }

}
