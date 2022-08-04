package com.bhw.pay.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.common.util.Md5Util;
import com.bhw.common.util.SmsUtil;
import com.bhw.pay.mapper.RefundRecordMapper;
import com.bhw.pay.mapper.WalletOrderMapper;
import com.bhw.pay.pojo.OrderBean;
import com.bhw.pay.pojo.RefundRecord;
import com.bhw.pay.pojo.WalletOrder;
import com.bhw.pay.service.OrderService;
import com.bhw.pay.vo.PayVo;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/pay")
public class WxPayAction {

    static Logger logger = LoggerFactory.getLogger(WxPayAction.class);

    @Value("${wxpay.backUrl}")
    private String backUrl;

    @Autowired
    private OrderService orderService;

    @Resource
    RefundRecordMapper refundRecordMapper;

    @Resource
    WalletOrderMapper walletOrderMapper;

    protected HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    protected HttpServletResponse getResponse() {
        HttpServletResponse request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        return request;
    }

    @RequestMapping("/wxPay")
    @ResponseBody
    public Response<Object> wxPay(Response<Object> response, OrderBean formVo,WalletOrder walletOrder,String projectId,Integer userId){
        Response<Object> resp = new Response<>();
        PayVo payVo = new PayVo();
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        try {
            String nonce_str = SmsUtil.getRandom(32)+"";
            String out_trade_no = formVo.getOrderId();
            String total_fee = (int)(Double.valueOf(formVo.getTotalPrice())*100)+"";

            String appid = "wx5664a8fe80c1cb85";
            String partnerid = "1578358901";
            String appkey = "Duijiepingtai2020trauphuayun0331";
            String body = formVo.getItemName();
            String stringA="appid="+appid+"&body="+body+"&mch_id="+partnerid+"&nonce_str="+nonce_str
                    +"&notify_url="+backUrl+"/pay/wxPayRet&out_trade_no="+out_trade_no+"&product_id="+projectId+"&spbill_create_ip="+getRequest().getRemoteAddr()
                    +"&total_fee="+total_fee+"&trade_type=NATIVE";
            String stringSignTemp=stringA+"&key="+appkey; //注：key为商户平台设置的密钥key +payBase.getNoncestr()
            String sign=Md5Util.encodeByMd5(stringSignTemp).toUpperCase();
            sign = Md5Util.MD5Encode(stringSignTemp).toUpperCase();
            Map<String, String> params = new HashMap<String, String>();
            params.put("appid", appid);
            params.put("body", body);
            params.put("mch_id", partnerid);
            params.put("nonce_str",nonce_str);
            params.put("notify_url", backUrl+"/pay/wxPayRet");
            params.put("out_trade_no", out_trade_no);
            params.put("product_id", projectId);
            params.put("sign", sign);
            params.put("spbill_create_ip", getRequest().getRemoteAddr());
            params.put("total_fee",total_fee);
            params.put("trade_type", "NATIVE");


            String xml = "<xml>" +
                    "<appid>"+ params.get("appid") +"</appid>"+
                    "<body>"+ params.get("body") +"</body>"+
                    "<mch_id>"+ params.get("mch_id") +"</mch_id>"+
                    "<nonce_str>"+ params.get("nonce_str") +"</nonce_str>"+
                    "<notify_url>"+ params.get("notify_url") +"</notify_url>"+
//	                "<openid>"+ params.get(key) +"</openid>"+
                    "<out_trade_no>"+ params.get("out_trade_no") +"</out_trade_no>"+
                    "<product_id>"+params.get("product_id")+"</product_id>"+
                    "<spbill_create_ip>"+ params.get("spbill_create_ip") +"</spbill_create_ip>"+
                    "<total_fee>"+ params.get("total_fee") + "" +"</total_fee>"+
                    "<trade_type>"+params.get("trade_type")+"</trade_type>"+
                    "<sign>"+ params.get("sign") +"</sign>"+
                    "</xml>";
            String responseStr = httpsRequest(url, "POST", xml);
            Map doXMLParse = doXMLParse(responseStr);
            logger.info("|微信下单Map结果|"+doXMLParse);
            if(((String)doXMLParse.get("result_code")).equalsIgnoreCase("SUCCESS")){
                payVo.setNoncestr(out_trade_no);
                payVo.setCodeUrl((String)doXMLParse.get("code_url"));
                payVo.setTimestamp((System.currentTimeMillis()/1000)+"");
                payVo.setSign((esing(payVo,appkey)));
                if(out_trade_no.startsWith("KJSC")){
                    Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
                    OrderBean one = orderService.getOne(queryWrapper);
                    if(null != one){
                        formVo.setId(one.getId());
                        formVo.setPayType(1);
                        orderService.upOrder(formVo);
                    }
                }else {
                    Wrapper<WalletOrder> queryWrapper =  new QueryWrapper<WalletOrder>(walletOrder);
                    WalletOrder one = walletOrderMapper.selectOne(queryWrapper);
                    if(null != one){
                        walletOrder.setId(one.getId());
                        walletOrder.setPayType(1);
                        walletOrderMapper.updateById(walletOrder);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setData(payVo);
        return resp;
    }



    public void wxRefund(RefundRecord refundRecord){
        Response<Object> response = new Response<>();
        String out_trade_no = refundRecord.getOrderId();
        String total_fee = (int)(Double.valueOf(refundRecord.getRefundPrice())*100)+"";
        String appid = "wx5664a8fe80c1cb85";
        String partnerid = "1578358901";
        String appkey = "Duijiepingtai2020trauphuayun0331";
        String nonce_str = SmsUtil.getRandom(32)+"";
        String stringA="appid="+appid+"&mch_id="+partnerid+"&nonce_str="+nonce_str
                +"&notify_url="+backUrl+"/pay/wxPayRet&out_refund_no="+refundRecord.getRefundOrderId()+"&out_trade_no="+out_trade_no
                +"&refund_fee="+total_fee+"&total_fee="+total_fee;
        String stringSignTemp=stringA+"&key="+appkey; //注：key为商户平台设置的密钥key +payBase.getNoncestr()
        String sign= Md5Util.MD5Encode(stringSignTemp).toUpperCase();

        String xml = "<xml>" +
                "<appid>"+ appid +"</appid>"+
                "<mch_id>"+ partnerid +"</mch_id>"+
                "<nonce_str>"+ nonce_str +"</nonce_str>"+
                "<notify_url>"+ backUrl+"/pay/wxPayRet" +"</notify_url>"+
                "<out_refund_no>"+refundRecord.getRefundOrderId()+"</out_refund_no>"+
                "<out_trade_no>"+ out_trade_no +"</out_trade_no>"+
                "<refund_fee>"+total_fee+"</refund_fee>"+
                "<total_fee>"+ total_fee + "" +"</total_fee>"+
                "<sign>"+ sign +"</sign>"+
                "</xml>";
//        String responseStr = httpsRequest("https://api.mch.weixin.qq.com/secapi/pay/refund", "POST", xml);
        logger.info("|退款xml|"+xml);
        String responseStr = null;
        try {
            responseStr = wxPayBack("https://api.mch.weixin.qq.com/secapi/pay/refund",xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(responseStr)){
            return ;
        }
        Map doXMLParse = null;
        try {
            doXMLParse = doXMLParse(responseStr);
            logger.info("|微信下单Map结果|"+doXMLParse);
            if(((String)doXMLParse.get("result_code")).equalsIgnoreCase("SUCCESS")){
                RefundRecord refundRecord2 = new RefundRecord();
                refundRecord2.setStatus(2);
                refundRecord2.setRefundOrderId(refundRecord.getRefundOrderId());
                RefundRecord refundRecord1 = new RefundRecord();
                refundRecord1.setRefundOrderId(refundRecord2.getRefundOrderId());
                refundRecord1.setOrderId(out_trade_no);
                refundRecordMapper.update(refundRecord2,new QueryWrapper<>(refundRecord1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = { "/wxPayRet", "/api/wxPayRet" })
    @ResponseBody
    public Response<Object> wxPayRet() throws Exception{
        Response<Object> response = new Response<Object>();
        // 从输入流读取返回内容
        InputStream inputStream = getRequest().getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        // 释放资源
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        OutputStream outputStream = getResponse().getOutputStream();
        // 注意编码格式
        outputStream.write("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>".getBytes("UTF-8"));
        outputStream.close();
        logger.info("|微信支付结果|"+buffer.toString());
        Map doXMLParse = doXMLParse(buffer.toString());
        if("SUCCESS".equals(doXMLParse.get("return_code"))){
            logger.info("|修改订单状态|"+ JSON.toJSONString(doXMLParse));
            String out_trade_no = (String) doXMLParse.get("out_trade_no");
            if(doXMLParse.containsKey("req_info")){
                return response;
            }
            boolean flag = true;
            if(doXMLParse.containsKey("out_refund_no")){
                RefundRecord refundRecord = new RefundRecord();
                refundRecord.setStatus(2);
                refundRecord.setRefundOrderId((String) doXMLParse.get("out_refund_no"));
                RefundRecord refundRecord1 = new RefundRecord();
                refundRecord1.setRefundOrderId(refundRecord.getRefundOrderId());
                refundRecord1.setOrderId(out_trade_no);
                refundRecordMapper.update(refundRecord,new QueryWrapper<>(refundRecord1));
                flag = false;
            }
            if(out_trade_no.startsWith("KJSC")){
                OrderBean formVo = new OrderBean();
                formVo.setOrderId(out_trade_no);
                Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
                OrderBean one = orderService.getOne(queryWrapper);
                if(null != one){
                    formVo.setId(one.getId());
                    formVo.setPayStatus(flag?2:3);
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
                    walletOrderMapper.updateById(walletOrder);
                }
            }
        }
        String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                + "<return_msg><![CDATA[OK]]></return_msg>"
                + "</xml> ";
        BufferedOutputStream out = new BufferedOutputStream(
                getResponse().getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        return response;
    }


    public static String esing(PayVo payVo,String key){
        String stringA="appid="+payVo.getAppid()+"&noncestr="+payVo.getNoncestr()+"&package=Sign=WXPay&partnerid="+payVo.getPartnerid()+"&prepayid="+payVo.getPrepayid()+
                "&timestamp="+payVo.getTimestamp()+"&key="+key;
        String sign= Md5Util.MD5Encode(stringA).toUpperCase();
        return sign;
    }

    // xml解析
    public static Map doXMLParse(String strxml) throws Exception {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        // 关闭流
        in.close();
        return m;
    }

    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }

    public static String wxPayBack(String url, String data) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        ClassPathResource classPathResource = new ClassPathResource("apiclient_cert.p12");
        String path1 =  classPathResource.getURI().toString();
        path1 = path1.replace("jar:file:", "").replace("bhw-pay.jar!/","");
        logger.info("|微信退款证书|"+path1);
        InputStream inputStream = new FileInputStream(path1);
        String result = "";
        try {
            keyStore.load(inputStream, "1578358901".toCharArray());
        } finally {
            inputStream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "1578358901".toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            StringEntity entitys = new StringEntity(data);
            httppost.setEntity((HttpEntity) entitys);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text = "";
                    String t = "";
                    while ((text = bufferedReader.readLine()) != null) {
                        t += text;
                    }
                    byte[] temp = t.getBytes("utf-8");//这里写原编码方式
                    String newStr = new String(temp, "utf-8");//这里写转换后的编码方式
                    result = newStr;
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        logger.info("|微信退款证书结果|"+result);
        return result;
    }

    //发起微信支付请求
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}"+ ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}"+ e);
        }
        return null;
    }
}


