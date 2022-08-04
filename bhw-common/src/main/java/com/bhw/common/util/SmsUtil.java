package com.bhw.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.util.HashMap;

public class SmsUtil {

    public static String getRandom(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {
            str+=(int)(Math.random()*10);
        }
        return str;
    }

    public static int sendSmsSecurityCode(String mobile,String content){
        int ret = 1;
        String url = "http://sh2.cshxsp.com/smsJson.aspx";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userid", "100");
        params.put("account", "jkwl342");
        params.put("password", SmsUtil.getMD5("qwe789"));
        params.put("content", content);
        params.put("mobile", mobile);
        params.put("action", "send");
        try {
            String string = new HttpUtils().get(url,params);
            JSONObject jsonObject = JSON.parseObject(string);
            if(StringUtils.equalsIgnoreCase((String) jsonObject.get("returnstatus"), "Success")){
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * MD5加密
     * @param message 要进行MD5加密的字符串
     * @return 加密结果为32位字符串
     */
    public static String getMD5(String message) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(message.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++)
            {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return md5StrBuff.toString().toUpperCase();//字母大写
    }

    public static void main(String[] args) {
        String random = SmsUtil.getRandom(4);
        int sendSmsSecurityCode = SmsUtil.sendSmsSecurityCode("13632717521", "【北部湾国际技术转移转化中心服务平台】您的短信验证码为"+random+",尊敬的用户请查收!");
    }

}
