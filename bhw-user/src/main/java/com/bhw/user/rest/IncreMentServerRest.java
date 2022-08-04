package com.bhw.user.rest;

import com.bhw.common.base.Response;
import com.bhw.user.pojo.IncreMentServer;
import com.bhw.user.service.IncreMentServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/increMent")
public class IncreMentServerRest {

    @Autowired
    private IncreMentServerService increMentServerService;

    /**
     * 新增用户服务
     * */
    @RequestMapping("/addIncreMentServer")
    @ResponseBody
    public Response<Object> addIncreMentServer(IncreMentServer incrementServer) {
        Response<Object> response = new Response<>();
        increMentServerService.addIncreMentServer(incrementServer);
        return response;
    }

    /**
     * 增值服务续费
     */
    @RequestMapping("/renewServer")
    @ResponseBody
    public Response<Object> renewServer(IncreMentServer increMentServer) {
        Response<Object> response = new Response<>();
        increMentServerService.renewIncreMentServer(increMentServer);
        return response;
    }

    /**
     * 用户关闭服务
     */
    @RequestMapping("/closeServer")
    @ResponseBody
    public Response<Object> closeServer(IncreMentServer increMentServer) {
        Response<Object> response = new Response<>();
        increMentServerService.closeServer(increMentServer);
        return response;
    }
}
