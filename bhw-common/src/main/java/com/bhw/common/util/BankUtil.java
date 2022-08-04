package com.bhw.common.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BankUtil {
    //显示银行对应的logo:https://apimg.alipay.com/combo.png?d=cashier&t=ABC（银行简称-大写）
    public static void main(String[] args) {
        System.out.println(getCardDetail("6210676802084484923"));
    }
    public static String getCardDetail(String cardNo) {
        // 创建HttpClient实例
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=";
        url+=cardNo;
        url+="&cardBinCheck=true";
        StringBuilder sb = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject=JSONObject.parseObject(sb.toString());
        if(jsonObject.getBoolean("validated")){
            return jsonObject.getString("bank");
        }
        return "";
    }

}
