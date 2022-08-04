package com.bhw.serverManage.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.IntellectualMapper;
import com.bhw.serverManage.mapper.RequireTypeMapper;
import com.bhw.serverManage.mapper.ServerBusinessMapper;
import com.bhw.serverManage.pojo.RequireTypeBean;
import com.bhw.serverManage.pojo.ServerBusinessBean;
import com.bhw.serverManage.service.ProjectDwService;
import com.bhw.serverManage.pojo.IntellectualTypeBean;
import com.bhw.serverManage.vo.AiListVo;
import com.bhw.serverManage.vo.OrderVo;
import com.bhw.serverManage.vo.RequireVo;
import com.bhw.serverManage.pojo.ServerProjectBean;
import com.bhw.serverManage.vo.TechAchievementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品数据仓库接口
 * */
@RestController
@RefreshScope
@RequestMapping("/dw")
public class ProjectDwRest {

    @Autowired
    private ProjectDwService projectDwService;

    @Resource
    IntellectualMapper intellectualMapper;

    @Resource
    ServerBusinessMapper serverBusinessMapper;

    @RequestMapping("/qryServiceDwList")
    @ResponseBody
    public Response<Object> selectServiceDwList(AiListVo serverProjectBean, Page page){
        Response<Object> response = new Response<>();
        List<AiListVo> serverProjectBeans = projectDwService.selectServiceDwList(serverProjectBean, page);
        response.setData(serverProjectBeans);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/qryProjectList")
    @ResponseBody
    public Response<Object> qryProjectList(ServerProjectBean serverProjectBean, Page page){
        Response<Object> response = new Response<>();
        List<ServerProjectBean> serverProjectBeans = projectDwService.qryIntellectuaProject(serverProjectBean, page);
        response.setData(serverProjectBeans);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/qryProjectOrderList")
    @ResponseBody
    public Response<Object> qryIntellectuaOrderList(OrderVo orderVo,Page page){
        Response<Object> response = new Response<>();
        List<OrderVo> orderVos = projectDwService.qryIntellectuaOrderList(orderVo, page);
        response.setData(orderVos);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/backQryOrderList")
    @ResponseBody
    public Response<Object> backQryOrderList(OrderVo orderVo,Page page){
        Response<Object> response = new Response<>();
        List<OrderVo> orderVos = projectDwService.backQryOrderList(orderVo, page);
        response.setData(orderVos);
        response.setPage(page);
        return response;
    }


    @RequestMapping("/qryRequireList")
    @ResponseBody
    public Response<Object> qryRequireList(RequireVo requireVo, Page page){
        Response<Object> response = new Response<>();
        List<RequireVo> orderVos = projectDwService.qryRequireList(requireVo, page);
        response.setData(orderVos);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/qryScienceRequireList")
    @ResponseBody
    public Response<Object> qryScienceRequireList(RequireVo requireVo, Page page){
        Response<Object> response = new Response<>();
        List<RequireVo> orderVos = projectDwService.qryScienceRequireList(requireVo, page);
        response.setData(orderVos);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/qryServiceDealList")
    @ResponseBody
    public Response<Object> qryServiceDealList(RequireVo requireVo, Page page){
        Response<Object> response = new Response<>();
        List<RequireVo> qryServiceDealList = projectDwService.qryServiceDealList(requireVo, page);
        response.setData(qryServiceDealList);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/qryTechAchievemnetList")
    @ResponseBody
    public Response<Object> qryTechAchievemnetList(TechAchievementVo techAchievementVo, Page page){
        Response<Object> response = new Response<>();
        List<TechAchievementVo> qryServiceDealList = projectDwService.qryTechAchievemnetList(techAchievementVo, page);
        response.setData(qryServiceDealList);
        response.setPage(page);
        return response;
    }


    @RequestMapping("/qryServerBusiness")
    @ResponseBody
    public Response<Object> qryServerBusiness(ServerBusinessBean serverBusinessBean){
        Response<Object> response = new Response<>();
        Wrapper query = new QueryWrapper(serverBusinessBean);
        List list = serverBusinessMapper.selectList(query);
        response.setData(list);
        return response;
    }

}
