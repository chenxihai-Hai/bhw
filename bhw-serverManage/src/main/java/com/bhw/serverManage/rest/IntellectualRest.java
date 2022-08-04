package com.bhw.serverManage.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.IntellectualMapper;
import com.bhw.serverManage.pojo.ServerProjectBean;
import com.bhw.serverManage.service.ProjectDwService;
import com.bhw.serverManage.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/intellectua")
public class IntellectualRest {

    @Autowired
    private ProjectDwService projectDwService;

    @Resource
    IntellectualMapper intellectualMapper;

    @RequestMapping("/addProject")
    @ResponseBody
    public Response<Object> addProject(ServerProjectBean serverProjectBean){
        Response<Object> response = new Response<>();
        projectDwService.addIntellectuaProject(serverProjectBean);
        return response;
    }

    @RequestMapping("/upProject")
    @ResponseBody
    public Response<Object> upProject(ServerProjectBean serverProjectBean){
        Response<Object> response = new Response<>();
        intellectualMapper.updateById(serverProjectBean);
        return response;
    }

    @RequestMapping("/batchUpStatus")
    @ResponseBody
    public Response<Object> batchUpStatus(String pIds,Integer status){
        Response<Object> response = new Response<>();
        if(null != status && 3== status){
            String[] split = pIds.split(",");
            for (String pId: split) {
                OrderVo orderVo = new OrderVo();
                orderVo.setProductId(pId);
                orderVo.setStatus(1);
                IPage<OrderVo> orderVoIPage = intellectualMapper.backQryOrderList(new Page<OrderVo>(1, 5), orderVo);
                List<OrderVo> records = orderVoIPage.getRecords();
                if(!records.isEmpty()){
                    response.setRetCode(-1);
                    response.setMessage(pId);
                    return response;
                }
            }
        }
        projectDwService.batchUpStatus(pIds,status);
        return response;
    }

    @RequestMapping("/batchUpBoutique")
    @ResponseBody
    public Response<Object> batchUpBoutique(String pIds,Integer status){
        Response<Object> response = new Response<>();
        projectDwService.batchUpBoutique(pIds,status);
        return response;
    }

    @RequestMapping("/batchUpAuditStatus")
    @ResponseBody
    public Response<Object> batchUpAuditStatus(String pIds,Integer status){
        Response<Object> response = new Response<>();
        projectDwService.batchUpAuditStatus(pIds,status);
        return response;
    }

    @RequestMapping("/delServerProject")
    @ResponseBody
    public Response<Object> delServerProject(ServerProjectBean serverProjectBean){
        Response<Object> response = new Response<>();
        OrderVo orderVo = new OrderVo();
        orderVo.setProductId(serverProjectBean.getId().toString());
        orderVo.setStatus(1);
        IPage<OrderVo> orderVoIPage = intellectualMapper.backQryOrderList(new Page<OrderVo>(1, 5), orderVo);
        List<OrderVo> records = orderVoIPage.getRecords();
        if(!records.isEmpty()){
            response.setRetCode(-1);
            return response;
        }
        intellectualMapper.deleteById(serverProjectBean.getId());
        return response;
    }
}
