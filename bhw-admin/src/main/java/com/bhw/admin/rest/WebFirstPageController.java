package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.NewsMapper;
import com.bhw.admin.mapper.TServerMapper;
import com.bhw.admin.mapper.UserMapper;
import com.bhw.admin.pojo.ListingExpert;
import com.bhw.admin.pojo.NewsBean;
import com.bhw.admin.pojo.TServerBean;
import com.bhw.admin.pojo.UserMbBean;
import com.bhw.admin.vo.NewsVo;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/first")
public class WebFirstPageController{
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private TServerMapper tServerMapper;
    @Autowired
    private UserMapper userMapper;

    /*查询动态
    *@param type 新闻类型(1.新闻动态 2.政策法规 3.科技服务 4.通知公告)
     */
    @RequestMapping("/getNewsManager")
    @ResponseBody
    public Response<Object> newsManager(@RequestParam(value = "type",required = false)String type,Page page){
        Response<Object> response = new Response<>();
        IPage<NewsVo> model=newsMapper.selectByType(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<NewsVo>(page.getCurPage(),page.getPageSize()),type);
        List<NewsVo>  list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        response.setData(list);
        response.setPage(page);
        return response;
    }
    @RequestMapping("/getCoreServer")
    @ResponseBody
    public Response<Object> coreServer(){
        Response<Object> response = new Response<>();
        List<TServerBean> list=tServerMapper.selectall();
        response.setData(list);
        return response;
    }

    /**
     * 查经纪人
     * @param brokerid 经纪人专业类型
     * @return
     */
    @RequestMapping("/getUserManager")
    @ResponseBody
    public Response<Object> userManager(@RequestParam(value = "brokerid",required = false)  String brokerid,Page page){
        Response<Object> response = new Response<>();
        IPage<UserMbBean> model=userMapper.selectByTypeAndBrokerid(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserMbBean>(page.getCurPage(),page.getPageSize()),brokerid);
        List<UserMbBean> list=model.getRecords();
        response.setData(list);
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        response.setPage(page);
        return response;
    }
    @RequestMapping("/getListingExpert")
    @ResponseBody
    public Response<Object> getListingExpert(Page page){
        Response<Object> response = new Response<>();
        IPage<ListingExpert> iPage = userMapper.selectListingExpert(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ListingExpert>(page.getCurPage(),page.getPageSize()));
//        ListingExpert list=userMapper.selectListingExpert();
        response.setData(iPage.getRecords());
        page.setCounts((int)iPage.getTotal(),page.getPageSize());
        page.setCurPage((int)iPage.getCurrent());
        response.setPage(page);
        return response;
    }
}
