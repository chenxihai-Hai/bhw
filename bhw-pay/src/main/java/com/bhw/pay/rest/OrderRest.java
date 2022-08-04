package com.bhw.pay.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.pay.mapper.OrderMapper;
import com.bhw.pay.mapper.WalletOrderMapper;
import com.bhw.pay.pojo.OrderBean;
import com.bhw.pay.pojo.OrderUser;
import com.bhw.pay.pojo.RefundRecord;
import com.bhw.pay.pojo.WalletOrder;
import com.bhw.pay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RefreshScope
@RequestMapping("/order")
public class OrderRest {

    @Autowired
    private OrderService orderService;

    @Resource
    WalletOrderMapper walletOrderMapper;

    @Autowired
    WxPayAction wxPayAction;

    @Autowired
    AliPayAction aliPayAction;

    @Resource
    OrderMapper orderMapper;

    @RequestMapping("/qryOrderInfo")
    @ResponseBody
    public Response<Object> qryOrderInfo(OrderBean orderBean){
        Response<Object> response = new Response<>();
        Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(orderBean);
        OrderBean one = orderService.getOne(queryWrapper);
        response.setData(one);
        return response;
    }

    @RequestMapping("/upOrderCloseSt")
    @ResponseBody
    public Response<Object> upOrderCloseSt(OrderBean orderBean){
        Response<Object> response = new Response<>();
        OrderBean orderBean1 = new OrderBean();
        orderBean1.setCloseStatus(2);
        orderMapper.update(orderBean1,new QueryWrapper<>(orderBean));
        return response;
    }

    @RequestMapping("/upOrderSt")
    @ResponseBody
    public Response<Object> upOrderSt(OrderBean orderBean){
        Response<Object> response = new Response<>();
        OrderBean orderBean1 = new OrderBean();
        orderBean1.setStatus(2);
        orderMapper.update(orderBean1,new QueryWrapper<>(orderBean));
        return response;
    }

    @RequestMapping("/qryWalletOrderInfo")
    @ResponseBody
    public Response<Object> qryWalletOrderInfo(WalletOrder walletOrder){
        Response<Object> response = new Response<>();
        Wrapper<WalletOrder> queryWrapper =  new QueryWrapper<WalletOrder>(walletOrder);
        WalletOrder one = walletOrderMapper.selectOne(queryWrapper);
        response.setData(one);
        return response;
    }


    @RequestMapping("/saveOrder")
    @ResponseBody
    public Response<Object> saveOrder(OrderBean orderBean){
        Response<Object> response = new Response<>();
        String he = "KJSC";
        String time = System.currentTimeMillis()+"";
        String productId = orderBean.getProductId();
        String out_trade_no = he+time+""+orderBean.getProductId().replaceAll(",","");
        orderBean.setOrderId(out_trade_no);
        orderBean.setOrderNum(time+productId.replaceAll(",",""));
        orderService.addOrder(orderBean);
        response.setData(orderBean);
        return response;
    }

    @RequestMapping("/refund")
    @ResponseBody
    public Response<Object> refund(RefundRecord refundRecord){
        Response<Object> response = new Response<>();
        OrderBean formVo = new OrderBean();
        formVo.setOrderId(refundRecord.getOrderId());
        Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
        OrderBean one = orderService.getOne(queryWrapper);
        if(null == one){
            return response;
        }
        Integer payType = one.getPayType();
        switch (payType){
            case 1:
                wxPayAction.wxRefund(refundRecord);
                break;
            case 2:
                refundRecord.setRefundOrderId(one.getOrderNum());
                aliPayAction.aliRefund(refundRecord);
        }
        return response;
    }

    @RequestMapping("/saveWalletOrder")
    @ResponseBody
    public Response<Object> saveWalletOrder(WalletOrder walletOrder){
        Response<Object> response = new Response<>();
        String he = "WT";
        String time = System.currentTimeMillis()+"";
        String productId = walletOrder.getProductId();
        String out_trade_no = he+time+""+walletOrder.getProductId().replaceAll(",","");
        walletOrder.setOrderId(out_trade_no);
        walletOrder.setOrderNum(time+productId.replaceAll(",",""));
        walletOrderMapper.insert(walletOrder);
        response.setData(walletOrder);
        return response;
    }
}
