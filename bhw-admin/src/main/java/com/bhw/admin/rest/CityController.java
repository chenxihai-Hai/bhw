package com.bhw.admin.rest;


import com.bhw.admin.pojo.CityBean;
import com.bhw.admin.pojo.ProvinceBean;
import com.bhw.admin.service.CityService;
import com.bhw.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    @RequestMapping("/qryProvinceList")
    @ResponseBody
    public Response<Object> qryProvinceList(){
        Response<Object> response = new Response<>();
        response.setData(cityService.qryProvinceList());
        return response;
    };

    @RequestMapping("/qryCityList")
    @ResponseBody
    public Response<Object> qryCityList(CityBean cityBean){
        Response<Object> response = new Response<>();
        response.setData(cityService.qryCityList(cityBean));
        return response;
    };
}
