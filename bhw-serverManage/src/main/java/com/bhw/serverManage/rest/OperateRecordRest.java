package com.bhw.serverManage.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.OperateRecordMapper;
import com.bhw.serverManage.pojo.OperateRecordBean;
import com.bhw.serverManage.pojo.RequireAdvisory;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/operate")
public class OperateRecordRest {

    @Resource
    OperateRecordMapper operateRecordMapper;

    @RequestMapping("/qryOperateRecord")
    @ResponseBody
    public Response<Object> qryOperateRecord(OperateRecordBean operateRecordBean, Page page){
        Response<Object> response = new Response<>();
        Wrapper query = new QueryWrapper(operateRecordBean);
        IPage<OperateRecordBean> brokerAdvisoryIPage = operateRecordMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<OperateRecordBean>(page.getCurPage(), page.getPageSize()), query);
        List<OperateRecordBean> records = brokerAdvisoryIPage.getRecords();
        page.setCounts((int)brokerAdvisoryIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)brokerAdvisoryIPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveOperateRecord")
    @ResponseBody
    public Response<Object> saveOperateRecord(OperateRecordBean operateRecordBean){
        Response<Object> response = new Response<>();
        operateRecordMapper.insert(operateRecordBean);
        return response;
    }

    @RequestMapping("/delOperateRecord")
    @ResponseBody
    public Response<Object> delOperateRecord(String ids,OperateRecordBean operateRecordBean){
        Response<Object> response = new Response<>();
        if(StringUtils.isNotEmpty(ids)){
            operateRecordMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
            return response;
        }
        Wrapper query = new QueryWrapper(operateRecordBean);
        operateRecordMapper.delete(query);
        return response;
    }
}
