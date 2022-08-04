package com.bhw.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThirdpartyJhUtil {

    /**
     * 身份证认证
     * 1：匹配 2：不匹配
     * */
    public static int idcardVerify(String name,String idcard){
        String url = "http://op.juhe.cn/idcard/query";
        Map<String,String> map = new HashMap<>();
        map.put("idcard",idcard);
        map.put("realname",name);
        map.put("key","e01ded9bdc8b6f0ff75687715f5d018f");
        return gerResult(url,map);
    }

    /**
     * 企业认证
     * 	status	int	匹配详情,1:全匹配;2:全不匹配;12:公司匹配,法人不匹配;13:公司不匹配,法人匹配
     * */
    public static int companyVerify(String keyword,String name,String oper_name){
        String url = "http://japi.juhe.cn/enterprise/ent3";
        Map<String,String> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("name",name);
        map.put("oper_name",oper_name);
        map.put("key","c696f525ddcf53a8640cbf08ff6e6890");
        return gerResult(url,map);
    }

    /**
     * 银行卡认证
     * 1:匹配 2:不匹配
     * */
    public static int bankCardVerify(String realname,String idcard,String bankcard,String mobile){
        String url = "http://v.juhe.cn/verifybankcard4/query";
        Map<String,String> map = new HashMap<>();
        map.put("realname",realname);
        map.put("idcard",idcard);
        map.put("bankcard",bankcard);
        map.put("mobile",mobile);
        map.put("key","aabf46016402a4746fa24f96571611d3");
        return gerResult(url,map);
    }

    public static int gerResult(String url,Map<String,String> map){
        HttpUtils httpUtils = new HttpUtils();
        try {
            String post = httpUtils.post(url, map);
            JSONObject jsonObject = JSON.parseObject(post);
            String reason = jsonObject.getString("reason");
            if(null != reason && "成功".equals(reason)){
                return jsonObject.getJSONObject("result").getInteger("res");
            }
            if(null != reason && "操作成功".equals(reason)){
                return jsonObject.getJSONObject("result").getInteger("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        int deng = ThirdpartyJhUtil.companyVerify("91440300MA5FLTK2W", "深圳砾智知识产权运营有限公司","翁治林");
        System.out.print(deng);
    }
}
