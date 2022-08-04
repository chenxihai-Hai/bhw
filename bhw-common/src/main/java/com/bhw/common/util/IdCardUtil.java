package com.bhw.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class IdCardUtil {

    private static final String URL_IDCARD = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";//身份证识别地址
    private static final String URL_ACCESSTOKEN = "https://aip.baidubce.com/oauth/2.0/token?"; // 百度AI开发平台 获取token的地址
    private static final String API_KEY = "sWQ5mwNdCTuP1EvGum3w3Y5c";    // 百度AI开发平台 获取的 API Key 更新为你注册的
    private static final String SECRET_KEY = "wFDWHI43hTm3ZrBNhcpyEBEQ4ghoxQ3U"; // 百度AI开发平台 获取的 Secret Key 更新为你注册的


    public static String getAccessToken() {
        String getAccessTokenUrl = URL_ACCESSTOKEN
                + "grant_type=client_credentials" // 1. grant_type为固定参数
                + "&client_id="+ API_KEY // 2. 官网获取的 API Key
                + "&client_secret=" + SECRET_KEY; // 3. 官网获取的 Secret Key
        String accessToken = "";
        try {
            URL realUrl = new URL(getAccessTokenUrl);

            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            System.err.println("result:" + result);

            JSONObject jsonObject = JSON.parseObject(result);
            accessToken = jsonObject.getString("access_token");
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return accessToken;
    }

    /**
     * 身份证识别请求
     * @param side 识别身份证正面 front;识别身份证背面 back;
     * @param fileBytes 图片
     * @return 返回身份证号码
     */
    public static String requestIdCard(String side,byte[] fileBytes,String accessToken) {
        String result = "";

        try {
            //1.请求获取结果
            String requestParams = "id_card_side=" + side
                    + "&" + URLEncoder.encode("image", "UTF-8")
                    + "=" + URLEncoder.encode(Base64Util.encode(fileBytes), "UTF-8");

            result = BaiduHttp.post(URL_IDCARD, accessToken, requestParams);//返回json格式的结果
            //2.解析结果
            JSONObject resultJson = JSON.parseObject(result);

            if(resultJson.containsKey("error_code") && resultJson.getString("error_code").equals("110")) {
                return requestIdCard(side,fileBytes,getAccessToken()) ;//重新请求
            }else {
                String words = "";
                if(resultJson.get("image_status") != null && resultJson.getString("image_status").equals("normal")) {//    正常
                    if("back".equals(side)){
                        words = side;
                    }else{
                        JSONObject jsonObject = resultJson.getJSONObject("words_result");
                        JSONObject idCardJson = jsonObject.getJSONObject("公民身份号码");
                        words = idCardJson.getString("words");
                    }
                }
                return words;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }


}