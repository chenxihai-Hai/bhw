package com.bhw.fiscalTax.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.fiscalTax.dto.releaseTaxReq;
import com.bhw.fiscalTax.mapper.FiscalTaxInfoMapper;
import com.bhw.fiscalTax.pojo.FiscalTaxInfo;
import com.bhw.fiscalTax.service.ReleaseMgrService;
import com.bhw.fiscalTax.vo.ServerBussinessVo;
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
    FiscalTaxInfoMapper fiscalTaxInfoMapper;

    @RequestMapping(value = "/getBussinessType",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getBussinessType(Integer type){
        Response<Object> response = new Response<>();
        List<ServerBussinessVo> list= releaseMgrService.getBussinessType(type);
        response.setData(list);
        return response;
    }

    @RequestMapping(value = "/releaseTaxServer",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> releaseTaxServer(releaseTaxReq req){
        Response<Object> response = new Response<>();
        boolean flag=releaseMgrService.saveReleaseInfo(req);
        response.setData(flag);
        return response;
    }

    @RequestMapping(value = "/upFiscalTaxInfo",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> upFiscalTaxInfo(FiscalTaxInfo req){
        Response<Object> response = new Response<>();
        fiscalTaxInfoMapper.updateById(req);
        return response;
    }

    @RequestMapping(value = "/getFiscalTaxInfo",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> getFiscalTaxInfo(FiscalTaxInfo req){
        Response<Object> response = new Response<>();
        response.setData(fiscalTaxInfoMapper.selectOne(new QueryWrapper<>(req)));
        return response;
    }

    @RequestMapping(value = "/delFiscalTaxInfo")
    @ResponseBody
    public Response<Object> delFiscalTaxInfo(String ids){
        Response<Object> response = new Response<>();
        fiscalTaxInfoMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }
}
