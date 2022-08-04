package com.bhw.user.rest;

import com.bhw.common.base.Response;
import com.bhw.user.service.BhwUserService;
import com.bhw.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private BhwUserService bhwUserService;

    @Value("${account_par_key}")
    public String priKey;

   /* @Value("${account.par.key.expire}")
    public String expire;*/
    /**
     * 登录(个人用户根据手机号登录，企业用户根据名称登录)
     * @param username 企业用户名称
     * @param phone 个人用户手机号
     * @param pwd
     * @return
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public Response<Object> userLogin(@RequestParam(value = "username",required = false) String username,
                                      @RequestParam(value = "phone",required = false) String phone,
                                      @RequestParam(value = "pwd",required = true) String pwd){
        Response<Object> response = new Response<>();
        String token= loginService.userLogin(username,phone,pwd);
        response.setData(token);
        return response;
    }

    @RequestMapping("/wxUserLogin")
    @ResponseBody
    public Response<Object> wxUserLogin(){
        Response<Object> response = new Response<>();
        return response;
    }

//    /**
//     *
//     * @param phone 手机号
//     * @param phoneCaptcha 手机验证码
//     * @return
//     */
//    @RequestMapping("/getbackPwd")
//    @ResponseBody
//    public Response<Object> getbackPwd( @RequestParam(value = "phone",required = false) String phone,
//                                        @RequestParam(value = "phoneCaptcha",required = false) String phoneCaptcha){
//        Response<Object> response = new Response<>();
//        loginService.getbackPwd(phone,phoneCaptcha,response);
//        return response;
//    }
}
