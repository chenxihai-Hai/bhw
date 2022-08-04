package com.bhw.fiscalTax.rest;

import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import com.bhw.fiscalTax.dto.ServerListPageReq;
import com.bhw.fiscalTax.dto.releaseTaxReq;
import com.bhw.fiscalTax.mapper.FiscalTaxInfoMapper;
import com.bhw.fiscalTax.service.SupplyMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/supplyMgr")
public class SupplyMgrRest {
    @Autowired
    SupplyMgrService supplyMgrService;

    @Resource
    FiscalTaxInfoMapper fiscalTaxInfoMapper;

    @RequestMapping(value = "/getServerListPage",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getServerListPage(ServerListPageReq req, Page page){
        Response<Object> response = new Response<>();
        response.setData(supplyMgrService.getServerList(req,page));
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/backSelectServerList",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> backSelectServerList(ServerListPageReq req, Page page){
        Response<Object> response = new Response<>();
        response.setData(supplyMgrService.backSelectServerList(req,page));
        response.setPage(page);
        return response;
    }


    @RequestMapping(value = "/serverTakeOff",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> serverTakeOff(String idList){
        Response<Object> response = new Response<>();
        List<Integer> list = new ArrayList<>();
        String[] split = idList.split(",");
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        boolean flag=supplyMgrService.serverTakeOff(list);
        response.setData(flag);
        return response;
    }

    @RequestMapping(value = "/infoAudit",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> infoAudit(Integer status,String idList){
        Response<Object> response = new Response<>();
        List<Integer> list = new ArrayList<>();
        String[] split = idList.split(",");
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        boolean flag=supplyMgrService.infoAudit(status,list);
        response.setData(flag);
        return response;
    }
}
