package com.bhw.user.rest;

import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.pojo.RefundRecord;
import com.bhw.user.service.RefundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/refundRecord")
public class RefundRecordRest {

    @Autowired
    private RefundRecordService refundRecordService;

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
     * 申请退款
     * */
    @RequestMapping("/saveRefundRecord")
    @ResponseBody
    public Response<Object> saveRefundRecord(RefundRecord refundRecord){
        Response<Object> response = new Response<>();
        String orderId = refundRecord.getOrderId();
        orderId = orderId.replace("KJSC","KJSCTK");
        refundRecord.setRefundOrderId(orderId);
        refundRecordService.saveRefundRecord(refundRecord);
        return response;
    }
}
