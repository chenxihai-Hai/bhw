package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.PatentMapper;
import com.bhw.admin.pojo.PatentBean;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/patent")
public class PatentController {

    @Resource
    PatentMapper patentMapper;

    @RequestMapping("/qryPatentList")
    @ResponseBody
    public Response<Object> qryPatentList(PatentBean patentBean, Page page){
        Wrapper<PatentBean> queryWrapper =  new QueryWrapper<PatentBean>(patentBean);
        Response<Object> response = new Response<>();
        IPage<PatentBean> qryAddressManageList = patentMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<PatentBean>(page.getCurPage(), page.getPageSize()), queryWrapper);
        List<PatentBean> records = qryAddressManageList.getRecords();
        page.setCounts((int)qryAddressManageList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryAddressManageList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/savePatentBean")
    @ResponseBody
    public Response<Object> savePatentBean(PatentBean patentBean){
        Response<Object> response = new Response<>();
        if(!patentBean.getList().isEmpty()){
            List<PatentBean> list = patentBean.getList();
            list.forEach(patentBean1 -> {
                patentMapper.insert(patentBean1);
            });
        }else{
            patentMapper.insert(patentBean);
        }
        return response;
    }

    @RequestMapping("/upPatentBean")
    @ResponseBody
    public Response<Object> upPatentBean(PatentBean patentBean){
        Response<Object> response = new Response<>();
        if(!patentBean.getList().isEmpty()){
            List<PatentBean> list = patentBean.getList();
            list.forEach(patentBean1 -> {
                patentMapper.updateById(patentBean1);
            });
        }else{
            patentMapper.updateById(patentBean);
        }
        return response;
    }

    @RequestMapping("/delPatentBean")
    @ResponseBody
    public Response<Object> delPatentBean(String idList){
        Response<Object> response = new Response<>();
        patentMapper.deleteBatchIds(Arrays.asList(idList.split(",")));
        return response;
    }
}
