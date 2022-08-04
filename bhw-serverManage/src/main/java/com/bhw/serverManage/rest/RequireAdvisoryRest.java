package com.bhw.serverManage.rest;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.RequireAdvisoryMapper;
import com.bhw.serverManage.pojo.RequireAdvisory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 需求承接咨询
 * */
@RestController
@RefreshScope
@RequestMapping("/requireAdvisory")
public class RequireAdvisoryRest {

    @Resource
    RequireAdvisoryMapper requireAdvisoryMapper;


    @RequestMapping("/saveRequireAdvisory")
    @ResponseBody
    public Response<Object> saveBrokerAdvisory(RequireAdvisory requireAdvisory){
        Response<Object> response = new Response<>();
        requireAdvisoryMapper.insert(requireAdvisory);
        return response;
    }

    @RequestMapping("/qryRequireAdvisory")
    @ResponseBody
    public Response<Object> qryBrokerAdvisory(RequireAdvisory requireAdvisory, Page page){
        Response<Object> response = new Response<>();
        IPage<RequireAdvisory> brokerAdvisoryIPage = requireAdvisoryMapper.selectRequireAdvisoryList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<RequireAdvisory>(page.getCurPage(), page.getPageSize()), requireAdvisory);
        List<RequireAdvisory> records = brokerAdvisoryIPage.getRecords();
        page.setCounts((int)brokerAdvisoryIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)brokerAdvisoryIPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }



}
