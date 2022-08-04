package com.bhw.admin.rest;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.dto.ListedAdvisoryReq;
import com.bhw.admin.dto.ListedResultReq;
import com.bhw.admin.mapper.ListedAdvisoryMapper;
import com.bhw.admin.mapper.ListedNoticeInfoMapper;
import com.bhw.admin.mapper.ListedTranstionMapper;
import com.bhw.admin.pojo.ListedAdvisory;
import com.bhw.admin.pojo.ListedNoticeInfo;
import com.bhw.admin.pojo.ListedTranstion;
import com.bhw.admin.pojo.NewsBean;
import com.bhw.admin.service.ListedTranstionService;
import com.bhw.admin.util.RedisUtil;
import com.bhw.admin.vo.ListedMessageVo;
import com.bhw.admin.vo.ListedNoticeBirefVo;
import com.bhw.admin.vo.ListedResultVo;
import com.bhw.admin.vo.ListedTranstionVo;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.util.SmsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RefreshScope
@RequestMapping("/listedTranstion")
public class ListedTransactionController {
//    public static Map<String,String> mapCach=new HashMap<>();

    @Autowired
    private ListedTranstionService listedTranstionService;

    @Resource
    private ListedTranstionMapper listedTranstionMapper;

    @Resource
    private ListedNoticeInfoMapper listedNoticeInfoMapper;

    @Resource
    private ListedAdvisoryMapper listedAdvisoryMapper;

    @Resource
    private RedisUtil redisUtil;
    /**
     * 正在挂牌交易数量
     */
    @RequestMapping("/listedOnNumber")
    @ResponseBody
    public Response<Object> listedOnNumber(String status) {
        Response<Object> response = new Response<>();
        int count = listedTranstionService.listedOnNumber(status);
        response.setData(count);
        return response;
    }

    /**
     * 挂牌交易信息
     */
    @RequestMapping(value = "/listedMessage", method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> listedMessage(Page page) {
        Response<Object> response = new Response<>();
        List<ListedMessageVo> list = listedTranstionService.listedMessage(page);
        response.setData(list);
        response.setPage(page);
        return response;
    }

    /**
     * 挂牌交易结果
     *
     * @param page
     * @param req
     * @return
     */
    @RequestMapping(value = "/listedResult", method = RequestMethod.GET)
    public Response<Object> listedResult(Page page, ListedResultReq req) {
        Response<Object> response = new Response<>();
        List<ListedResultVo> list = listedTranstionService.listedResult(page, req);
        response.setData(list);
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/backQryListedList", method = RequestMethod.GET)
    public Response<Object> backQryListedList(Page page, ListedTranstionVo req) {
        Response<Object> response = new Response<>();
        List<ListedTranstionVo> list = listedTranstionService.backQryListedList(page, req);
        response.setData(list);
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/saveListedTran")
    public Response<Object> saveListedTran(ListedTranstion listedTranstion) {
        Response<Object> response = new Response<>();
        ListedTranstion listedTranstion1 = new ListedTranstion();
        listedTranstion1.setNumber(listedTranstion.getNumber());
        ListedTranstion listedTranstion2 = listedTranstionMapper.selectOne(new QueryWrapper<>(listedTranstion1));
        if(null == listedTranstion2){
            listedTranstionMapper.insert(listedTranstion);
            return response;
        }
        response.setRetCode(-1);
        return response;
    }

    @RequestMapping(value = "/upListedTran")
    public Response<Object> upListedTran(ListedTranstion listedTranstion) {
        Response<Object> response = new Response<>();
        listedTranstionMapper.updateById(listedTranstion);
        return response;
    }

    @RequestMapping(value = "/saveListedNot")
    public Response<Object> saveListedNot(ListedNoticeInfo listedNoticeInfo) {
        Response<Object> response = new Response<>();
        listedNoticeInfoMapper.insert(listedNoticeInfo);
        return response;
    }


    @RequestMapping("/birefNotice")
    @ResponseBody
    public Response<Object> birefNotice(Page page) {
        Response<Object> response = new Response<>();
        List<ListedNoticeBirefVo> list = listedTranstionService.birefNotice(page);
        response.setData(list);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/noticeInfo")
    @ResponseBody
    public Response<Object> noticeInfo(Integer info_id) {
        Response<Object> response = new Response<>();
        ListedNoticeInfo data = listedTranstionService.noticeInfo(info_id);
        response.setData(data);
        return response;
    }

    @RequestMapping("/getcaptchaCode")      //获取验证码
    @ResponseBody
    public Response<Object> getcaptchaCode(String phone) {
        Response<Object> response = new Response<>();
        boolean ismatch= Pattern.matches("^1[3456789]\\d{9}$",phone);
        if(ismatch){
            String random = SmsUtil.getRandom(4);
            System.out.println("验证码|"+random);
            int flag= SmsUtil.sendSmsSecurityCode(phone, "【北部湾中心】"+random+"(动态验证码，5分钟内有效)，请妥善保存此验证码，以免泄露。如非本人操作，请忽略。");
            if(flag==0){
//                mapCach.put(phone,random);
                redisUtil.set(phone,random,300);
                response.setData(true);
            }else {
                response.setData(false);
            }
        }
        return response;

    }

    @RequestMapping("/listedAdvisory")
    @ResponseBody
    public Response<Object> listedAdvisory(ListedAdvisoryReq req) {
        Response<Object> response = new Response<>();
        boolean flag=listedTranstionService.listedAdvisory(req);
        response.setData(flag);
        return response;
    }

    @RequestMapping("/uplistedAdvisory")
    @ResponseBody
    public Response<Object> uplistedAdvisory(ListedAdvisory req) {
        Response<Object> response = new Response<>();
        listedAdvisoryMapper.updateById(req);
        return response;
    }

    @RequestMapping("/listedAdvisoryList")
    @ResponseBody
    public Response<Object> listedAdvisoryList(ListedAdvisory req,Page page) {
        Response<Object> response = new Response<>();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page ipage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(page.getCurPage(),page.getPageSize());
        Wrapper<ListedAdvisory> objectQueryWrapper = new QueryWrapper<ListedAdvisory>(req);
        ((QueryWrapper<ListedAdvisory>) objectQueryWrapper).orderByDesc("createTime");
        IPage<ListedAdvisory> selectPage = listedAdvisoryMapper.selectPage(ipage, objectQueryWrapper);
        List<ListedAdvisory> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }
}