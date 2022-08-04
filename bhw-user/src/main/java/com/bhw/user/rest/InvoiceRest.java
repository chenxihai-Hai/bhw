package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.InvoiceRecordMapper;
import com.bhw.user.mapper.InvoiceUserMapper;
import com.bhw.user.pojo.InvoiceRecord;
import com.bhw.user.pojo.InvoiceUser;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RefreshScope
@RequestMapping("/invoice")
public class InvoiceRest {

    @Resource
    InvoiceUserMapper invoiceUserMapper;

    @Resource
    InvoiceRecordMapper invoiceRecordMapper;

    @RequestMapping("/qryInvoiceUserList")
    @ResponseBody
    public Response<Object> qryInvoiceUserList(InvoiceUser invoiceUser, Page page){
        Wrapper<InvoiceUser> queryWrapper =  new QueryWrapper<InvoiceUser>(invoiceUser);
        Response<Object> response = new Response<>();
        IPage<InvoiceUser> invoiceUserIPage = invoiceUserMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<InvoiceUser>(page.getCurPage(), page.getPageSize()), queryWrapper);
        page.setCounts((int)invoiceUserIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)invoiceUserIPage.getCurrent());
        response.setData(invoiceUserIPage.getRecords());
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveInvoiceUser")
    @ResponseBody
    public Response<Object> saveInvoiceUser(InvoiceUser invoiceUser){
        Response<Object> response = new Response<>();
        invoiceUserMapper.insert(invoiceUser);
        return response;
    }

    @RequestMapping("/saveInvoiceRecord")
    @ResponseBody
    public Response<Object> saveInvoiceRecord(InvoiceRecord invoiceRecord){
        Response<Object> response = new Response<>();
        invoiceRecordMapper.insert(invoiceRecord);
        return response;
    }

    @RequestMapping("/upInvoiceUser")
    @ResponseBody
    public Response<Object> upInvoiceUser(InvoiceUser invoiceUser){
        Response<Object> response = new Response<>();
        invoiceUserMapper.updateById(invoiceUser);
        return response;
    }

    @RequestMapping("/delInvoiceUser")
    @ResponseBody
    public Response<Object> delInvoiceUser(InvoiceUser invoiceUser){
        Response<Object> response = new Response<>();
        invoiceUserMapper.deleteById(invoiceUser.getId());
        return response;
    }

    @RequestMapping("/qryInvoicRecordList")
    @ResponseBody
    public Response<Object> qryInvoicRecordList(InvoiceRecord invoiceRecord, Page page){
        Wrapper<InvoiceRecord> queryWrapper =  new QueryWrapper<InvoiceRecord>(invoiceRecord);
        Response<Object> response = new Response<>();
        IPage<InvoiceRecord> invoiceUserIPage = invoiceRecordMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<InvoiceRecord>(page.getCurPage(), page.getPageSize()), queryWrapper);
        page.setCounts((int)invoiceUserIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)invoiceUserIPage.getCurrent());
        response.setData(invoiceUserIPage.getRecords());
        response.setPage(page);
        return response;
    }

}
