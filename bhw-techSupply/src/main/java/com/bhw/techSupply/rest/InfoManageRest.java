package com.bhw.techSupply.rest;


import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import com.bhw.techSupply.dto.InfoAuditReq;
import com.bhw.techSupply.dto.InfoListReq;
import com.bhw.techSupply.service.InfoManageService;
import com.bhw.techSupply.vo.InfoListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/infoManage")
public class InfoManageRest {
    @Autowired
    private InfoManageService infoManageService;

    @RequestMapping(value = "/getInfoList",method = RequestMethod.GET)
    @ResponseBody
    public Response<Object> getInfoList(InfoListReq req, Page page){
        Response<Object> response = new Response<>();
        List<InfoListVo> vo = infoManageService.getInfoList(req,page);
        response.setPage(page);
        response.setData(vo);
        return response;
    }

    @RequestMapping(value = "/infoAudit",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> infoAudit(@MultiRequestBody InfoAuditReq req){
        Response<Object> response = new Response<>();
        response.setData(infoManageService.infoAudit(req));
        return response;
    }
}
