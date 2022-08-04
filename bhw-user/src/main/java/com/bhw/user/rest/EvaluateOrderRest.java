package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.EvaluateOrderMapper;
import com.bhw.user.pojo.AddressManage;
import com.bhw.user.pojo.EvaluateOrder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/evaluateOrder")
public class EvaluateOrderRest {

    @Resource
    private EvaluateOrderMapper evaluateOrderMapper;

    @RequestMapping("/qryEvaluateOrderList")
    @ResponseBody
    public Response<Object> qryEvaluateOrderList(EvaluateOrder evaluateOrder, Page page){
        Response<Object> response = new Response<>();
        IPage<EvaluateOrder> qryEvaluateOrderList = evaluateOrderMapper.qryEvaluateOrderList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<EvaluateOrder>(page.getCurPage(), page.getPageSize()), evaluateOrder);
        List<EvaluateOrder> records = qryEvaluateOrderList.getRecords();
        page.setCounts((int)qryEvaluateOrderList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryEvaluateOrderList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/statisticsEvaluate")
    @ResponseBody
    public Response<Object> statisticsEvaluate(EvaluateOrder evaluateOrder){
        Response<Object> response = new Response<>();
        EvaluateOrder evaluateOrder1 = evaluateOrderMapper.statisticsEvaluate(evaluateOrder);
        response.setData(evaluateOrder1);
        return response;
    }


    @RequestMapping("/saveEvaluateOrder")
    @ResponseBody
    public Response<Object> saveEvaluateOrder(EvaluateOrder evaluateOrder){
        Response<Object> response = new Response<>();
        evaluateOrderMapper.insert(evaluateOrder);
        return response;
    }

    @RequestMapping("/upEvaluateOrder")
    @ResponseBody
    public Response<Object> upEvaluateOrder(EvaluateOrder evaluateOrder){
        Response<Object> response = new Response<>();
        evaluateOrderMapper.updateById(evaluateOrder);
        return response;
    }

    @RequestMapping("/delEvaluateOrder")
    @ResponseBody
    public Response<Object> delEvaluateOrder(EvaluateOrder evaluateOrder){
        Response<Object> response = new Response<>();
        evaluateOrderMapper.deleteById(evaluateOrder.getId());
        return response;
    }
}
