package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.mapper.NewsMapper;
import com.bhw.admin.pojo.BrokerAdvisory;
import com.bhw.admin.pojo.NewsBean;
import com.bhw.admin.pojo.UserMbBean;
import com.bhw.admin.vo.NewsContentvO;
import com.bhw.admin.vo.NewsListVo;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
@RestController
@RefreshScope
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsMapper newsMapper;

    @RequestMapping(value = "/qryNewsList",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> qryNewsList(NewsBean newsBean, com.bhw.common.base.Page p,String title){
        Response<Object> response = new Response<>();
        Page page = new Page(p.getCurPage(),p.getPageSize());
        Wrapper<NewsBean> objectQueryWrapper = new QueryWrapper<NewsBean>(newsBean);
        if(StringUtils.isNotEmpty(title)){
            ((QueryWrapper<NewsBean>) objectQueryWrapper).like("subject",title);
        }
        if(StringUtils.isNotEmpty(newsBean.getImg())){
            newsBean.setImg(null);
            ((QueryWrapper<NewsBean>) objectQueryWrapper).isNotNull("img");
            ((QueryWrapper<NewsBean>) objectQueryWrapper).ne("img","");
        }
        ((QueryWrapper<NewsBean>) objectQueryWrapper).orderByDesc("createTime");
        IPage<NewsBean> newsBeanIPage = newsMapper.selectPage(page, objectQueryWrapper);
        List<NewsBean> records = newsBeanIPage.getRecords();
        p.setCounts((int)newsBeanIPage.getTotal(),p.getPageSize());
        p.setCurPage((int)newsBeanIPage.getCurrent());
        response.setData(records);
        response.setPage(p);
        return response;
    }

    @RequestMapping(value = "/saveNewsInfo")
    @ResponseBody
    public Response<Object> saveNewsInfo(NewsBean newsBean){
        Response<Object> response = new Response<>();
        newsMapper.insert(newsBean);
        return response;
    }

    @RequestMapping(value = "/upNewsInfo")
    @ResponseBody
    public Response<Object> upNewsInfo(NewsBean newsBean){
        Response<Object> response = new Response<>();
        newsMapper.updateById(newsBean);
        if(StringUtils.isEmpty(newsBean.getImg())){
            newsMapper.upNewsInfo(newsBean.getId());
        }
        return response;
    }

    @RequestMapping(value = "/delNewsInfo")
    @ResponseBody
    public Response<Object> delNewsInfo(String ids){
        Response<Object> response = new Response<>();
        newsMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }

    /**
     * 分页查询 查询新闻列表
     * */
    @RequestMapping(value = "/newsListPage",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> newsListPage( com.bhw.common.base.Page page,String type){
        Response<Object> response = new Response<>();
        IPage<NewsListVo> listVoIPage=newsMapper.selectNewsListPage(new Page<NewsListVo>(page.getCurPage(),page.getPageSize()),type);
        List<NewsListVo> list = listVoIPage.getRecords();
        page.setCounts((int)listVoIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)listVoIPage.getCurrent());
        response.setData(list);
        response.setPage(page);
        return response;
    }
    /**
     *查询新闻内容
     * */
    @RequestMapping(value = "/newsContentPage",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> newsContent(@RequestParam(value = "id",required = true) Integer id){
        Response<Object> response = new Response<>();
        NewsContentvO contentvO=newsMapper.selectContentById(id);
        response.setData(contentvO);
        return response;
    }
}
