package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.PatentMapper;
import com.bhw.admin.mapper.ReportMapper;
import com.bhw.admin.pojo.PatentBean;
import com.bhw.admin.pojo.ReportBean;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportRest {

    @Resource
    ReportMapper reportMapper;

    @RequestMapping("/saveReport")
    @ResponseBody
    public Response<Object> saveReport(ReportBean reportBean){
        Response<Object> response = new Response<>();
        reportMapper.insert(reportBean);
        return response;
    }

    @RequestMapping("/disposeReport")
    @ResponseBody
    public Response<Object> disposeReport(ReportBean reportBean){
        Response<Object> response = new Response<>();
        reportMapper.updateById(reportBean);
        return response;
    }

    @RequestMapping("/qryReportList")
    @ResponseBody
    public Response<Object> qryReportList(ReportBean reportBean, Page page){
        Wrapper<ReportBean> queryWrapper =  new QueryWrapper<ReportBean>(reportBean);
        Response<Object> response = new Response<>();
        ((QueryWrapper<ReportBean>) queryWrapper).orderByDesc("createTime");
        IPage<ReportBean> qryAddressManageList = reportMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ReportBean>(page.getCurPage(), page.getPageSize()), queryWrapper);
        List<ReportBean> records = qryAddressManageList.getRecords();
        page.setCounts((int)qryAddressManageList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryAddressManageList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }
}
