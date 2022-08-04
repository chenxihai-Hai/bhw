package com.bhw.serverManage.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.RequireTypeMapper;
import com.bhw.serverManage.mapper.ScienceRequireTypeMapper;
import com.bhw.serverManage.pojo.IntellectualTypeBean;
import com.bhw.serverManage.pojo.RequireTypeBean;
import com.bhw.serverManage.pojo.ScienceRequireTypeBean;
import com.bhw.serverManage.service.ProjectDwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/dataType")
public class DataTypeRest {

    @Autowired
    private ProjectDwService projectDwService;

    @Resource
    RequireTypeMapper requireTypeMapper;

    @Resource
    ScienceRequireTypeMapper scienceRequireTypeMapper;

    @RequestMapping("/qryRequireTypeList")
    @ResponseBody
    public Response<Object> qryRequireTypeList(RequireTypeBean requireTypeBean){
        Response<Object> response = new Response<>();
        Wrapper<RequireTypeBean> queryWrapper =  new QueryWrapper<RequireTypeBean>(requireTypeBean);
//        ((QueryWrapper<RequireTypeBean>) queryWrapper).orderByDesc("createTime");
        List<RequireTypeBean> requireTypeBeans = requireTypeMapper.selectList(queryWrapper);
        response.setData(requireTypeBeans);
        return response;
    }

    @RequestMapping("/qryScienceRequireTypeList")
    @ResponseBody
    public Response<Object> qryScienceRequireTypeList(ScienceRequireTypeBean scienceRequireTypeBean){
        Response<Object> response = new Response<>();
        Wrapper<ScienceRequireTypeBean> queryWrapper =  new QueryWrapper<ScienceRequireTypeBean>(scienceRequireTypeBean);
        List<ScienceRequireTypeBean> requireTypeBeans = scienceRequireTypeMapper.selectList(queryWrapper);
        response.setData(requireTypeBeans);
        return response;
    }

    @RequestMapping("/qryIntellectualType")
    @ResponseBody
    public Response<Object> qryIntellectualType(IntellectualTypeBean intellectualTypeBean){
        Response<Object> response = new Response<>();
        List<IntellectualTypeBean> intellectualTypeBeans = projectDwService.qryIntellectualType(intellectualTypeBean);
        response.setData(intellectualTypeBeans);
        return response;
    }
}
