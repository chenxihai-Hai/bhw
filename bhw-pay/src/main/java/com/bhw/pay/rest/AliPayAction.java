package com.bhw.pay.rest;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.pay.config.AlipayConfig;
import com.bhw.pay.mapper.RefundRecordMapper;
import com.bhw.pay.mapper.WalletOrderMapper;
import com.bhw.pay.pojo.OrderBean;
import com.bhw.pay.pojo.RefundRecord;
import com.bhw.pay.pojo.WalletOrder;
import com.bhw.pay.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/aliPay")
public class AliPayAction {

    @Autowired
    private OrderService orderService;

    @Resource
    RefundRecordMapper refundRecordMapper;

    @Resource
    WalletOrderMapper walletOrderMapper;

    Logger logger = LoggerFactory.getLogger(AliPayAction.class);

    @RequestMapping("/pay")
    public void payController(HttpServletRequest request, HttpServletResponse response, OrderBean formVo, WalletOrder walletOrder, String projectId, Integer userId) throws IOException {
        String out_trade_no = formVo.getOrderId();

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        if(out_trade_no.startsWith("KJSC")){
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
        }else{
            alipayRequest.setReturnUrl(AlipayConfig.wt_return_url);
        }
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
//        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
        String total_amount = formVo.getTotalPrice();
        //订单名称，必填
        String subject = formVo.getItemName();
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            if(out_trade_no.startsWith("KJSC")){
                Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
                OrderBean one = orderService.getOne(queryWrapper);
                if(null != one){
                    formVo.setId(one.getId());
                    formVo.setPayType(2);
                    orderService.upOrder(formVo);
                }
            }else{
                Wrapper<WalletOrder> queryWrapper =  new QueryWrapper<WalletOrder>(walletOrder);
                WalletOrder one = walletOrderMapper.selectOne(queryWrapper);
                if(null != one){
                    walletOrder.setId(one.getId());
                    walletOrder.setPayType(2);
                    walletOrderMapper.updateById(walletOrder);
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        logger.info("|请求支付宝下单|"+form);
        response.setContentType("text/html;charset=" + AlipayConfig.charset);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    public void aliRefund(RefundRecord refundRecord){
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+refundRecord.getOrderId()+"\"," +
//                "\"trade_no\":\""+refundRecord.getRefundOrderId()+"\"," +
                "\"refund_amount\":"+refundRecord.getRefundPrice()+"" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            if(response.isSuccess()){
                String out_trade_no = response.getOutTradeNo();
                RefundRecord refund = new RefundRecord();
                refund.setStatus(2);
                RefundRecord refundRecord1 = new RefundRecord();
                refundRecord1.setOrderId(out_trade_no);
                refundRecordMapper.update(refund,new QueryWrapper<>(refundRecord1));
                OrderBean formVo = new OrderBean();
                formVo.setOrderId(out_trade_no);
                Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
                OrderBean one = orderService.getOne(queryWrapper);
                if(null != one){
                    formVo.setId(one.getId());
                    formVo.setPayStatus(3);
                    orderService.upOrder(formVo);
                }
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/notify_url")
    public void aliNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        logger.info("|支付宝支付结果异步通知|");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        logger.info("|支付宝支付结果异步通知|"+ JSON.toJSONString(requestParams));
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */
        logger.info("|数据验证结果|"+signVerified);
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            logger.info("|订单交易状态|"+trade_status);
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                if(out_trade_no.startsWith("KJSC")){
                    OrderBean formVo = new OrderBean();
                    formVo.setOrderId(out_trade_no);
                    Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
                    OrderBean one = orderService.getOne(queryWrapper);
                    logger.info("|查询订单|"+JSON.toJSONString(one));
                    if(null != one){
                        formVo.setId(one.getId());
                        formVo.setPayStatus(2);
                        formVo.setOrderNum(trade_no);
                        orderService.upOrder(formVo);
                    }
                }else{
                    WalletOrder walletOrder = new WalletOrder();
                    walletOrder.setOrderId(out_trade_no);
                    Wrapper<WalletOrder> queryWrapper =  new QueryWrapper<WalletOrder>(walletOrder);
                    WalletOrder one = walletOrderMapper.selectOne(queryWrapper);
                    if(null != one){
                        walletOrder.setId(one.getId());
                        walletOrder.setPayStatus(2);
                        walletOrder.setOrderNum(trade_no);
                        walletOrderMapper.updateById(walletOrder);
                    }
                }
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            response.getWriter().print("success");

        }else {//验证失败
//            out.println("fail");
            response.getWriter().print("fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
    }

    @RequestMapping("/aliReturn")
    public void aliReturn(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        logger.info("|支付宝支付结果通知|"+ JSON.toJSONString(requestParams));
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            OrderBean formVo = new OrderBean();
            formVo.setOrderId(out_trade_no);
            Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
            OrderBean one = orderService.getOne(queryWrapper);
            if(null != one && null != one.getPayStatus() && 1==one.getPayStatus().intValue()){
                formVo.setId(one.getId());
                formVo.setPayStatus(2);
                orderService.upOrder(formVo);
            }
            response.sendRedirect("/bhw/userCenter?id="+out_trade_no);
//            response.getWriter().print("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
        }else {
            response.getWriter().print("验签失败");
        }
    }
}
