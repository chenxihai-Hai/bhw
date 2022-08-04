package com.bhw.user.rest;

import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import com.bhw.user.mapper.ServerHistoryMapper;
import com.bhw.user.pojo.ServerHistory;
import com.bhw.user.service.ServerOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/serverOperate")
public class ServerOperateRest {
    @Autowired
    private ServerOperateService serverOperateService;

    @Resource
    ServerHistoryMapper serverHistoryMapper;

    @RequestMapping(value = "/qryList",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> qryList(@RequestParam(value = "userid",required =true) Integer userid,
                                    @RequestParam(value = "operateType",required =true)Integer operateType) {
        Response<Object> response = new Response<>();
        response.setData(serverOperateService.qryList(userid,operateType));
        return response;
    }

    @RequestMapping(value = "/qryRecordList",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> qryRecordList(ServerHistory serverHistory, Page page){
        Response<Object> response = new Response<>();
        List<ServerHistory> serverHistories = serverOperateService.qryServerHistoryList(serverHistory, page);
        response.setData(serverHistories);
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/qryRecordDateList",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> qryRecordDateList(ServerHistory serverHistory, Page page){
        Response<Object> response = new Response<>();
        List<ServerHistory> serverHistories = serverOperateService.qryStatisticsDate(serverHistory, page);
        response.setData(serverHistories);
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/deleteRecord",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> deleteRecord( @MultiRequestBody List<Integer> list) {
        Response<Object> response = new Response<>();
        response.setData(serverOperateService.deleteRecord(list));
        return response;
    }

    @RequestMapping(value = "/deleteRecordByIds",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> deleteRecord(String ids) {
        Response<Object> response = new Response<>();
        response.setData(serverHistoryMapper.deleteBatchIds(Arrays.asList(ids.split(","))));
        return response;
    }

    @RequestMapping(value = "/saveRecord")
    @ResponseBody
    public Response<Object> saveRecord(ServerHistory serverHistory){
        Response<Object> response = new Response<>();
        serverOperateService.saveRecord(serverHistory);
        return response;
    }

}
