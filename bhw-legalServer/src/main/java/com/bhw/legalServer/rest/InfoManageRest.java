package com.bhw.legalServer.rest;

import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import com.bhw.legalServer.dto.InfoAuditReq;
import com.bhw.legalServer.dto.InfoListReq;
import com.bhw.legalServer.service.InfoManageService;
import com.bhw.legalServer.vo.InfoListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
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
