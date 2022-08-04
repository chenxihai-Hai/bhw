package com.bhw.bhwtechology.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.bhwtechology.mapper.ServerLeaveMsgMapper;
import com.bhw.bhwtechology.pojo.ServerLeaveMsg;
import com.bhw.bhwtechology.servicce.LeaveMgService;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/leaveMg")
public class LeaveMgRest {
    @Autowired
    LeaveMgService leaveMgService;

    @Resource
    private ServerLeaveMsgMapper serverLeaveMsgMapper;

    @RequestMapping(value = "/getLeaveMg",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getLeaveMg(Integer type,Integer serverId){
        Response<Object> response = new Response<>();
        List<ServerLeaveMsg> list= leaveMgService.getServerLeaveMsg(type,serverId);
        response.setData(list);
        return response;
    }

    @RequestMapping(value = "/saveLeaveMsg",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> saveLeaveMsg(ServerLeaveMsg serverLeaveMsg){
        Response<Object> response = new Response<>();
        leaveMgService.saveServerLeaveMsg(serverLeaveMsg);
        return response;
    }

    @RequestMapping(value = "/qryLeaveMsg")
    @ResponseBody
    public Response<Object> qryLeaveMsg(ServerLeaveMsg serverLeaveMsg, Page page){
        Response<Object> response = new Response<>();
        Wrapper queryWapper =  new QueryWrapper<ServerLeaveMsg>(serverLeaveMsg);
        if(null != serverLeaveMsg.getType() && 8==serverLeaveMsg.getType()){
            serverLeaveMsg.setType(null);
            ((QueryWrapper) queryWapper).in("type", 8, 9);
        }
        IPage<ServerLeaveMsg> selectPage = serverLeaveMsgMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerLeaveMsg>(page.getCurPage(), page.getPageSize()), queryWapper);
        List<ServerLeaveMsg> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return  response;
    }
}
