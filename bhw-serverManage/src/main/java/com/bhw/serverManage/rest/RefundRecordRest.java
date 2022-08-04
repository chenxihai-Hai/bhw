package com.bhw.serverManage.rest;

import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.RefundRecordMapper;
import com.bhw.serverManage.pojo.RefundRecord;
import com.bhw.serverManage.service.RefundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/refundRecord")
public class RefundRecordRest {

    @Autowired
    private RefundRecordService refundRecordService;

    @Resource
    RefundRecordMapper refundRecordMapper;

    /**
     * 查询退款列表
     * */
    @RequestMapping("/qryRefundRecordList")
    @ResponseBody
    public Response<Object> qryRefundRecordList(RefundRecord refundRecord, Page page){
        Response<Object> response = new Response<>();
        List<RefundRecord> refundRecords = refundRecordService.qryRefundRecordList(refundRecord, page);
        response.setData(refundRecords);
        response.setPage(page);
        return response;
    }


    @RequestMapping("/backQryRefundRecordList")
    @ResponseBody
    public Response<Object> backQryRefundRecordList(RefundRecord refundRecord, Page page){
        Response<Object> response = new Response<>();
        List<RefundRecord> refundRecords = refundRecordService.backQryRefundRecordList(refundRecord, page);
        response.setData(refundRecords);
        response.setPage(page);
        return response;
    }

    /**
     * 退款详情
     * */
    @RequestMapping("/getRefundRecordDet")
    @ResponseBody
    public Response<Object> getRefundRecordDet(RefundRecord refundRecord){
        Response<Object> response = new Response<>();
        RefundRecord refundRecord1 = refundRecordService.getRefundRecord(refundRecord);
        response.setData(refundRecord1);
        return response;
    }

    /**
     * 进行退款
     * */
    @RequestMapping("/upRefundRecord")
    @ResponseBody
    public Response<Object> upRefundRecord(RefundRecord refundRecord){
        Response<Object> response = new Response<>();
        refundRecordMapper.updateById(refundRecord);
        return response;
    }
}
