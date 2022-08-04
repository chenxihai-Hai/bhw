package com.bhw.techSupply.rest;

import com.bhw.common.base.Response;
import com.bhw.techSupply.pojo.ServerLeaveMsg;
import com.bhw.techSupply.service.LeaveMgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/leaveMg")
public class LeaveMgRest {
    @Autowired
    LeaveMgService leaveMgService;
    @RequestMapping(value = "/getLeaveMg",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getLeaveMg(Integer type,Integer serverId){
        Response<Object> response = new Response<>();
        List<ServerLeaveMsg> list= leaveMgService.getServerLeaveMsg(type,serverId);
        response.setData(list);
        return response;
    }
}
