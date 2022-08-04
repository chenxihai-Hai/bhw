package com.bhw.admin.rest;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.BrokerAdvisoryMapper;
import com.bhw.admin.mapper.BrokerLeaveMsgMapper;
import com.bhw.admin.mapper.ServerAdvisoryMapper;
import com.bhw.admin.pojo.BrokerAdvisory;
import com.bhw.admin.pojo.BrokerLeaveMsg;
import com.bhw.admin.pojo.ServerAdvisory;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 经纪人咨询
 * */
@RestController
@RefreshScope
@RequestMapping("/brokerAdvisory")
public class BrokerAdvisoryRest {

    @Resource
    BrokerAdvisoryMapper brokerAdvisoryMapper;

    @Resource
    BrokerLeaveMsgMapper brokerLeaveMsgMapper;

    @Resource
    private ServerAdvisoryMapper serverAdvisoryMapper;

    @RequestMapping("/saveBrokerAdvisory")
    @ResponseBody
    public Response<Object> saveBrokerAdvisory(BrokerAdvisory brokerAdvisory){
        Response<Object> response = new Response<>();
        brokerAdvisoryMapper.insert(brokerAdvisory);
        return response;
    }

    @RequestMapping("/qryBrokerAdvisory")
    @ResponseBody
    public Response<Object> qryBrokerAdvisory(BrokerAdvisory brokerAdvisory, Page page){
        Response<Object> response = new Response<>();
        IPage<BrokerAdvisory> brokerAdvisoryIPage = brokerAdvisoryMapper.selectBrokerAdvisoryList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BrokerAdvisory>(page.getCurPage(), page.getPageSize()), brokerAdvisory);
        List<BrokerAdvisory> records = brokerAdvisoryIPage.getRecords();
        page.setCounts((int)brokerAdvisoryIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)brokerAdvisoryIPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveBrokerLeaveMsg")
    @ResponseBody
    public Response<Object> saveBrokerLeaveMsg(BrokerLeaveMsg brokerLeaveMsg){
        Response<Object> response = new Response<>();
        brokerLeaveMsgMapper.insert(brokerLeaveMsg);
        ServerAdvisory serverAdvisory = new ServerAdvisory();
        serverAdvisory.setBorkerId(brokerLeaveMsg.getBrokerId());
        serverAdvisory.setServerRemark(brokerLeaveMsg.getContent());
        serverAdvisory.setServerName(brokerLeaveMsg.getRequireMsg());
        serverAdvisory.setUserName(brokerLeaveMsg.getUserName());
        serverAdvisory.setPhoneNo(brokerLeaveMsg.getPhoneNo());
        serverAdvisoryMapper.insert(serverAdvisory);
        return response;
    }

}
