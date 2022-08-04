package com.bhw.admin.rest;

import com.bhw.common.base.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RefreshScope
@RequestMapping("/language")
public class LanguageRest {

   /* @Value("${language}")
    private String chLg;*/

    @RequestMapping("/getLanguageConfig")
    @ResponseBody
    public Response<Object> getLanguageConfig(){
        Response<Object> response = new Response<>();
//        response.setData(chLg);
        return response;
    }
}
