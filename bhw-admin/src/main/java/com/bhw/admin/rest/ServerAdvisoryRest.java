package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.ServerAdvisoryMapper;
import com.bhw.admin.pojo.BrokerAdvisory;
import com.bhw.admin.pojo.ServerAdvisory;
import com.bhw.admin.util.RedisUtil;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.util.SmsUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务咨询
 * */
@RestController
@RefreshScope
@RequestMapping("/advisory")
public class ServerAdvisoryRest {

//    static Map<String,String> mapCach = new HashMap<>();

    @Resource
    private ServerAdvisoryMapper serverAdvisoryMapper;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/sendPhoneCode")
    @ResponseBody
    public Response<Object> sendPhoneCode(String phoneNo){
        Response<Object> response = new Response<>();
        String random = SmsUtil.getRandom(4);
        System.out.println("手机验证码:"+random);
        int flag= SmsUtil.sendSmsSecurityCode(phoneNo, "【北部湾中心】"+random+"(动态验证码，5分钟内有效)，请妥善保存此验证码，以免泄露。如非本人操作，请忽略。");
        if(flag==0){
            redisUtil.set(phoneNo,random,300);
        }
        response.setData(flag);
        return response;
    }

    @RequestMapping("/saveServerAdvisory")
    @ResponseBody
    public Response<Override> saveServerAdvisory(ServerAdvisory serverAdvisory,String verifyCode){
        Response<Override> response = new Response<>();
        String s = redisUtil.get(serverAdvisory.getPhoneNo());
        if(null == s){
            response.setRetCode(-1);
            response.setMessage("验证码已失效,请重新发送!");
            return response;
        }
        if(!s.equals(verifyCode)){
            response.setRetCode(-1);
            response.setMessage("验证码输入有误!");
            return response;
        }
        redisUtil.del(serverAdvisory.getPhoneNo());
        serverAdvisoryMapper.insert(serverAdvisory);
        return response;
    }

    @RequestMapping("/qryServerAdvisoryList")
    @ResponseBody
    public Response<Object> qryServerAdvisoryList(ServerAdvisory serverAdvisory, Page page){
        Response<Object> response = new Response<>();
        IPage<ServerAdvisory> selectServerAdvisoryList = serverAdvisoryMapper.selectServerAdvisoryList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerAdvisory>(page.getCurPage(), page.getPageSize()), serverAdvisory);
        List<ServerAdvisory> records = selectServerAdvisoryList.getRecords();
        page.setCounts((int)selectServerAdvisoryList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectServerAdvisoryList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/upServerAdvisory")
    @ResponseBody
    public Response<Object> upServerAdvisory(ServerAdvisory serverAdvisory){
        Response<Object> response = new Response<>();
        serverAdvisoryMapper.updateById(serverAdvisory);
        return  response;
    }

    @RequestMapping("/delServerAdvisory")
    @ResponseBody
    public Response<Object> delServerAdvisory(String ids){
        Response<Object> response = new Response<>();
        serverAdvisoryMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return  response;
    }
}
