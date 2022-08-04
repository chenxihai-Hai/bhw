package com.bhw.bhwtechology.rest;

import com.bhw.bhwtechology.dto.ServerListPageReq;
import com.bhw.bhwtechology.servicce.SupplyMgrService;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/supplyMgr")
public class SupplyMgrRest {
    @Autowired
    SupplyMgrService supplyMgrService;

    @RequestMapping(value = "/getServerListPage",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getServerListPage(ServerListPageReq req, Page page){
        Response<Object> response = new Response<>();
        response.setData(supplyMgrService.getServerList(req,page));
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

}
