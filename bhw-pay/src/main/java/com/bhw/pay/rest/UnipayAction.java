package com.bhw.pay.rest;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.pay.DemoBase;
import com.bhw.pay.common.SDKConstants;
import com.bhw.pay.config.SDKConfig;
import com.bhw.pay.pojo.OrderBean;
import com.bhw.pay.service.AcpService;
import com.bhw.pay.service.OrderService;
import com.bhw.pay.util.CertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RefreshScope
@RequestMapping("/uniPay")
public class UnipayAction {

    @Autowired
    private OrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(UnipayAction.class);
   /* @PostConstruct
    public void  init(){
        System.out.println("银联支付初始化");
        SDKConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载acp_sdk.properties文件
        CertUtil.init();
    }*/

    private String merId = "777290058169305"; //自己的商户号
    // private String merId = "777290058110048";

    private String redictUrl = "http://www.baidu.com";

    @RequestMapping("/pay")
    public void payController(HttpServletRequest request, HttpServletResponse response, OrderBean formVo, String projectId, Integer userId) throws IOException {
        String total_fee = (int)(Double.valueOf(formVo.getTotalPrice())*100)+"";
        String txnAmt = null;  //订单金额
        String txnTime = null; //订单发送时间
        String orderId = formVo.getOrderId();
        //订单发送时间用于查询用
        Date sendPaymentDate = new Date();
        txnTime = formatTime(sendPaymentDate);

        Map<String, String> requestData = new HashMap<>();

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        requestData.put("version", DemoBase.version);   			  //版本号，全渠道默认值
        requestData.put("encoding", DemoBase.encoding); 			  //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        requestData.put("txnType", "01");               			  //交易类型 ，01：消费
        requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
        requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
        requestData.put("channelType", "08");           			  //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机

        /***商户接入参数***/
        requestData.put("merId", merId);    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("accessType", "0");             			  //接入类型，0：直连商户
        requestData.put("orderId", orderId);                          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("txnTime", txnTime);        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）
        requestData.put("txnAmt", txnAmt);             			      //交易金额，单位分，不要带小数点
        //requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
        //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        requestData.put("frontUrl", DemoBase.frontUrl);

        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
        //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
        //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
        requestData.put("backUrl", DemoBase.backUrl);

        // 订单超时时间。
        // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
        // 此时间建议取支付时的北京时间加15分钟。
        // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
        requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));

        //////////////////////////////////////////////////
        //
        //       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
        //
        //////////////////////////////////////////////////

        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        Map<String, String> submitFromData = AcpService.sign(requestData,DemoBase.encoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,DemoBase.encoding);   //生成自动跳转的Html表单

        //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
        response.getWriter().write(html);
    }

    /**
     * 后台回调
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/backRcvResponse", method = {RequestMethod.GET, RequestMethod.POST})
    public void backRcvResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("BackRcvResponse接收后台通知开始");

        String encoding = request.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(request);

        logger.info(JSON.toJSONString(reqParam));

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                valideData.put(key, value);
            }
        }

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            logger.info("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            logger.info("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

            String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取

            String respCode = valideData.get("respCode");
            String txnAmt = valideData.get("txnAmt");
            BigDecimal txnAmount = (new BigDecimal(txnAmt)).multiply(new BigDecimal(0.01));

            String queryId = valideData.get("queryId");
            String traceTime = valideData.get("traceTime");
            String payCardNo = valideData.get("payCardNo");
            String payCardType = valideData.get("payCardType"); //支付卡类型
            String paymentMethodMethod; //PayPaymentMethod里面的method字段
            if(StringUtils.isEmpty(payCardType)){
                paymentMethodMethod = "UNION";  //对之前代码做兼容，如果没有支付卡类型的情况走默认
            }else{
                paymentMethodMethod = "UNION-" + payCardType;
            }

            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
            if("00".equals(respCode)){  // 00 交易成功
                OrderBean formVo = new OrderBean();
                formVo.setOrderId(orderId);
                Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(formVo);
                OrderBean one = orderService.getOne(queryWrapper);
                if(null != one){
                    formVo.setId(one.getId());
                    formVo.setPayStatus(2);
                    orderService.upOrder(formVo);
                }
                //todo 若交易成功
            }else if("A6".equals(respCode)){  // A6 部分成功

            }

        }
        logger.info("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200  状态码
        response.getWriter().print("ok");
    }

    /**
     * 前台回调
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/frontRcvResponse", method = RequestMethod.POST)
    public void frontRcvResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("FrontRcvResponse前台接收报文返回开始");

        String encoding = request.getParameter(SDKConstants.param_encoding);
        logger.info("返回报文中encoding=[" + encoding + "]");
        String pageResult = "";
        if (DemoBase.encoding.equalsIgnoreCase(encoding)) {
            pageResult = "/utf8_result.jsp";
        } else {
            pageResult = "/gbk_result.jsp";
        }
        Map<String, String> respParam = getAllRequestParam(request);

        // 打印请求报文
        logger.info(JSON.toJSONString(respParam));

        Map<String, String> valideData = null;
        StringBuffer page = new StringBuffer();
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = respParam.entrySet()
                    .iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                page.append("<tr><td width=\"30%\" align=\"right\">" + key
                        + "(" + key + ")</td><td>" + value + "</td></tr>");
                valideData.put(key, value);
            }
        }
        if (!AcpService.validate(valideData, encoding)) {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            logger.info("验证签名结果[失败].");
        } else {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            logger.info("验证签名结果[成功].");
            System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取

            String respCode = valideData.get("respCode");
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
        }
        request.setAttribute("result", page.toString());
        request.getRequestDispatcher(pageResult).forward(request, response);

        logger.info("FrontRcvResponse前台接收报文返回结束");

    }

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                //System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }



    //收费比率 精确到分保留两位小数四舍五入
    private BigDecimal getFeeAmount(BigDecimal amount, BigDecimal feeRatio, BigDecimal feeMax) {
        BigDecimal fee = new BigDecimal(0);
        if(null == amount || null == feeRatio) return fee;
        //金额乘以费率 = 手续费
        fee = amount.multiply(feeRatio);
        //最大值为feeMax
        if(null != feeMax && feeMax.compareTo(new BigDecimal("0")) >= 0) fee = fee.max(feeMax);
        //设置精确到分并四舍五入
        fee = fee.setScale(4, BigDecimal.ROUND_HALF_UP);
        return fee;
    }

    /**
     * 时间格式化
     * @param date
     * @return
     */
    private static String formatTime(Date date){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }
    private static Date formatTime(String dateStr){
        if(null == dateStr) return null;
        if(dateStr.length() == 14){
            try {
                return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            try {
                return new SimpleDateFormat("MMddHHmmss").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }

        }

    }
}
