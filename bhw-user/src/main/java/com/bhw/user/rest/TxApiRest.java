package com.bhw.user.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.common.util.HttpUtils;
import com.bhw.common.util.jwt.JWTHelper;
import com.bhw.common.util.jwt.JWTInfo;
import com.bhw.user.mapper.BhwUserMapper;
import com.bhw.user.pojo.BhwUser;
import com.bhw.user.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/txApi")
public class TxApiRest {
    Logger logger = LoggerFactory.getLogger(TxApiRest.class);
//    String appid = "wx3bfa010b497322b6";
//    String secret = "f31c4ccd76739557139cf6962f7c267b";

    @Value("${wx_appid}")
    public String appid;

    @Value("${wx_secret}")
    public String secret;

    @Value("${account_par_key}")
    public String priKey;

    @Value("${account_par_key_expire}")
    public String expire;

    @Resource
    BhwUserMapper bhwUserMapper;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/getWxAccessTokenApi")
    @ResponseBody
    public Response<Object> getWxAccessTokenApi(String code){
        Response<Object> response = new Response<>();
        HttpUtils httpUtils = new HttpUtils();
        String info = redisUtil.get(code);
        try {
            JSONObject jsonObject = null;
            if(StringUtils.isNotEmpty(info)){
                jsonObject = JSON.parseObject(info);
            }else{
                String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
                Map<String,String> map = new HashMap<>();
                map.put("appid",appid);
                map.put("secret",secret);
                map.put("code",code);
                map.put("grant_type","authorization_code");
                String s = httpUtils.get(url, map);
                jsonObject = JSON.parseObject(s);
                redisUtil.set(code,s,300);
            }
            logger.info("|微信登录授权|"+jsonObject.toJSONString()+"|CODE:"+code);
            if(jsonObject.containsKey("access_token")){
                BhwUser bhwUser = new BhwUser();
                bhwUser.setWx(jsonObject.getString("openid"));
                Wrapper<BhwUser> queryWrapper =  new QueryWrapper<BhwUser>(bhwUser);
                BhwUser bhwUser1 = bhwUserMapper.selectOne(queryWrapper);
                logger.info("|查询平台是否有该微信记录|"+bhwUser1);
                if(null == bhwUser1 || null == bhwUser1.getId()){
                    bhwUser1 = new BhwUser();
                    getWxUserInfo(jsonObject.getString("access_token"),bhwUser.getWx(),bhwUser1);
                }else{
                    try {
                        String token = JWTHelper.generateToken(new JWTInfo(bhwUser1.getUserName(), bhwUser1.getId() + "", bhwUser1.getPhoneNo()),priKey,Integer.valueOf(expire));
                        bhwUser1.setToken(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                response.setData(bhwUser1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping("/bindOpenId")
    @ResponseBody
    public Response<Object> bindOpenId(BhwUser bhwUser){
        Response<Object> response = new Response<>();
        BhwUser bhwUser1 = new BhwUser();
        bhwUser1.setAccount(bhwUser.getAccount());
        BhwUser bhwUser2 = bhwUserMapper.selectOne(new QueryWrapper<>(bhwUser1));
//        BhwUser bhwUser2 = bhwUserMapper.selectByPhone(bhwUser.getPhoneNo());
        if(null == bhwUser2 || null == bhwUser2.getId()){
            response.setRetCode(-1);
            return response;
        }
        if(StringUtils.isNotEmpty(bhwUser.getWx())){
            bhwUser2.setWx(bhwUser.getWx());
        }
        if(StringUtils.isNotEmpty(bhwUser.getQq())){
            bhwUser2.setQq(bhwUser.getQq());
        }
        bhwUserMapper.updateById(bhwUser2);
        try {
            String token = JWTHelper.generateToken(new JWTInfo(bhwUser2.getUserName(), bhwUser2.getId() + "", bhwUser2.getPhoneNo()),priKey,Integer.valueOf(expire));
            bhwUser2.setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(bhwUser2);
        return response;
    }


    @RequestMapping("/unbind")
    @ResponseBody
    public Response<Object> unbind(BhwUser user){
        Response<Object> response = new Response<>();
        bhwUserMapper.updateUserTxAccount(user);
        return response;
    }

    @RequestMapping("/saveUserInfo")
    @ResponseBody
    public Response<Object> saveUserInfo(BhwUser user){
        Response<Object> response = new Response<>();
        bhwUserMapper.insert(user);
        String token = null;
        try {
            token = JWTHelper.generateToken(new JWTInfo(user.getUserName(), user.getId() + "", user.getPhoneNo()),priKey,Integer.valueOf(expire));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setToken(token);
        response.setData(user);
        return response;
    }

    @RequestMapping("/qryQqUserInfo")
    @ResponseBody
    public Response<Object> qryQqUserInfo(BhwUser user){
        Response<Object> response = new Response<>();
        Wrapper<BhwUser> queryWrapper =  new QueryWrapper<BhwUser>(user);
        BhwUser bhwUser = bhwUserMapper.selectOne(queryWrapper);
        if(null != bhwUser && null != bhwUser.getId()){
            String token = null;
            try {
                token = JWTHelper.generateToken(new JWTInfo(bhwUser.getUserName(), bhwUser.getId() + "", bhwUser.getPhoneNo()),priKey,Integer.valueOf(expire));
            } catch (Exception e) {
                e.printStackTrace();
            }
            bhwUser.setToken(token);
            response.setData(bhwUser);
            return response;
        }
        return response;
    }

    public void getWxUserInfo(String access_token,String openid,BhwUser user){
        HttpUtils httpUtils = new HttpUtils();
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,String> map = new HashMap<>();
        map.put("access_token",access_token);
        map.put("openid",openid);
        try {
            String s = httpUtils.get(url, map);
            JSONObject json = JSON.parseObject(s);
            logger.info("|根据微信token获取用户信息|"+s);
            if(null != json.getString("openid")){
                user.setWx(json.getString("openid"));
                user.setPhoto(json.getString("headimgurl"));
                user.setSex(json.getInteger("sex"));
                user.setNickName(json.getString("nickname"));
                user.setUserName(json.getString("nickname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
