package com.bhw.user.rest;

import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.UserOrderMapper;
import com.bhw.user.service.UserOrderService;
import com.bhw.user.vo.UserOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/userOrder")
public class UserOrderRest {

    @Autowired
    private UserOrderService userOrderService;

    @Resource
    UserOrderMapper userOrderMapper;

    /**
     * 查询用户订单列表
     * */
    @RequestMapping("/qryUserOrderList")
    @ResponseBody
    public Response<Object> qryUserOrderList(UserOrderVo userOrderVo, Page page){
        Response<Object> response = new Response<>();
        List<UserOrderVo> userOrderVos = userOrderService.qryUserOrderList(userOrderVo, page);
        response.setData(userOrderVos);
        response.setPage(page);
        return response;
    }

    /**
     * 修改用户订单状态
     * */
    @RequestMapping("/upUserOrderStatus")
    @ResponseBody
    public Response<Object> upUserOrderStatus(UserOrderVo userOrderVo){
        Response<Object> response = new Response<>();
        userOrderService.upUserOrderStatus(userOrderVo);
        return response;
    }

    /**
     * 保存用户订单相关协议
     * */
    @RequestMapping("/upUserOrderProtocol")
    @ResponseBody
    public Response<Object> upUserOrderProtocol(UserOrderVo userOrderVo){
        Response<Object> response = new Response<>();
        userOrderMapper.upUserOrderProtocol(userOrderVo);
        return response;
    }

}
