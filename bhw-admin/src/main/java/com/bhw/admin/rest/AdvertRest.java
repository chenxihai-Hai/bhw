package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.AdvertMapper;
import com.bhw.admin.pojo.AdvertBean;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/advert")
public class AdvertRest {

    @Resource
    AdvertMapper advertMapper;

    @RequestMapping("/qryAdvertInfo")   //查询
    @ResponseBody
    public Response<Object> qryAdvertInfo(AdvertBean advertBean, Page page){
        Response<Object> response = new Response<>();
        Wrapper<AdvertBean> queryW =  new QueryWrapper<>(advertBean);
        ((QueryWrapper<AdvertBean>) queryW).orderByDesc("createTime");
        IPage<AdvertBean> selectPage = advertMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<AdvertBean>(page.getCurPage(), page.getPageSize()),queryW );
        List<AdvertBean> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }


    @RequestMapping("/saveAdvertInfo")      //保存
    @ResponseBody
    public Response<Object> saveAdvertInfo(AdvertBean advertBean){
        Response<Object> response = new Response<>();
        advertMapper.insert(advertBean);
        return response;
    }

    @RequestMapping("/upAdvertInfo")         //上传
    @ResponseBody
    public Response<Object> upAdvertInfo(AdvertBean advertBean){
        Response<Object> response = new Response<>();
        advertMapper.updateById(advertBean);
        return response;
    }

    @RequestMapping("/delAdvertInfo")        //删除
    @ResponseBody
    public Response<Object> upAdvertInfo(String ids){
        Response<Object> response = new Response<>();
        advertMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }
}
