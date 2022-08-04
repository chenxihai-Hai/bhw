package com.bhw.admin.rest;

import com.bhw.admin.dto.ResourceAreaRangeReq;
import com.bhw.admin.mapper.ResourceAreaMapper;
import com.bhw.admin.pojo.ResourceAreaBean;
import com.bhw.admin.service.ResourceAreaService;
import com.bhw.admin.vo.AreaTopFive;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/resourceArea")
public class ResourceAreaController {

    @Autowired
    ResourceAreaService areaService;

    @Resource
    ResourceAreaMapper resourceAreaMapper;

    /**
     * 按类型查找资源总数
     * @param type 类型（1高企，2机构）
     * @return
     */
    @RequestMapping(value = "/getResourceCount",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getResourceCount(@RequestParam(value = "type",required = true) Integer type){
        Response<Object> response = new Response<>();
        int count=areaService.getResourceCount(type);
        response.setData(count);
        return response;
    }

    /**
     * 统计地区前五名
     * @param type 类型（1高企，2机构）
     * @return
     */
    @RequestMapping(value = "/getResourceProvince",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getResourceProvince(@RequestParam(value = "type",required = true) Integer type){
        Response<Object> response = new Response<>();
        List<AreaTopFive> list=areaService.getResourceProvince(type);
        response.setData(list);
        return response;
    }

    @RequestMapping(value = "/getResourceAreaRange",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getResourceAreaRange(ResourceAreaRangeReq req,
                                                  Page page){
        Response<Object> response = new Response<>();
        List<ResourceAreaBean> list=areaService.getResourceAreaRange(req,page);
        Map<String,Object> result=new HashMap<>();
        result.put("dataList",list);
        result.put("size",list.size());
        response.setData(result);
        response.setPage(page);
        return response;
    }

    @RequestMapping(value = "/saveResourceArea")
    @ResponseBody
    public Response<Object> saveResourceArea(ResourceAreaBean resourceAreaBean){
        Response<Object> response = new Response<>();
        resourceAreaMapper.insert(resourceAreaBean);
        return response;
    }

    @RequestMapping(value = "/upResourceArea")
    @ResponseBody
    public Response<Object> upResourceArea(ResourceAreaBean resourceAreaBean){
        Response<Object> response = new Response<>();
        resourceAreaMapper.updateById(resourceAreaBean);
        return response;
    }

    @RequestMapping(value = "/delResourceArea")
    @ResponseBody
    public Response<Object> delResourceArea(String idList){
        Response<Object> response = new Response<>();
        resourceAreaMapper.deleteBatchIds(Arrays.asList(idList.split(",")));
        return response;
    }

    @RequestMapping(value = "/addData",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> addData(MultipartFile file)throws IOException{
        Response<Object> response = new Response<>();
        String fileName=file.getOriginalFilename();
        boolean flag=areaService.addData(file,fileName);
        return response;
    }
}
