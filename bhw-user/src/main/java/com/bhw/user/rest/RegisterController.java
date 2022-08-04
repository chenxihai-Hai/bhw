package com.bhw.user.rest;

import com.alibaba.fastjson.JSON;
import com.bhw.common.base.Response;
import com.bhw.user.dto.EnterpriseRegisterReq;
import com.bhw.user.dto.PersonalRegisterReq;
import com.bhw.user.mapper.CompanyCardMapper;
import com.bhw.user.pojo.BhwCompanyCard;
import com.bhw.user.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/register")
public class RegisterController {
    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private  RegisterService registerService;

    @Autowired
    private CompanyCardMapper cardMapper;
    /**
     * 获取验证码
     * @param randomCode 随机码
     * @param response
     * @return
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(@RequestParam(value = "randomCode",required = true) String randomCode,
                        HttpServletResponse response){
        try {
            String vCode = registerService.captchaOutput(randomCode, response);
            logger.info("userService.captchaOutput({}):{}", randomCode, vCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 验证验证码并获取手机验证码
     * @param randomCode
     * @param vCode
     * @return
     */
    @RequestMapping(value = "/checkCaptcha", method = RequestMethod.GET)
    public Response<Object> chechCaptcha(@RequestParam(value = "randomCode",required = true) String randomCode,
                                         @RequestParam(value = "vCode",required = true) String vCode,
                                         @RequestParam(value = "phone",required = true) String phone){
        Boolean flag = registerService.captchaCheck(randomCode,vCode,phone);
        Response<Object> response = new Response<>();
        response.setData(flag);
        return response;
    }

    /**
     * 验证验证码
     * @param
     * @param vCode
     * @return
     */
    @RequestMapping(value = "/viCaptchaCheckByCode", method = RequestMethod.GET)
    public Response<Object> viCaptchaCheckByCode(String vCode, String phone){
        Boolean flag = registerService.viCaptchaCheckByCode(vCode,phone);
        Response<Object> response = new Response<>();
        response.setData(flag);
        return response;
    }
    /**
     *个人注册
     * @return
     */
    @RequestMapping("/personalRegister")
    @ResponseBody
    public Response<Object> personalRegister(PersonalRegisterReq personalRegisterReq){
        Response<Object> response = new Response<>();
        registerService.personalRegister(personalRegisterReq,response);
        return response;
    }

    /**
     * 公司机构注册
     * @param registerReq
     * @return
     */
    @RequestMapping("/enterpriseRegister")
    @ResponseBody
    public Response<Object> enterpriseRegister(EnterpriseRegisterReq registerReq){
        Response<Object> response = new Response<>();
        return registerService.enterpriseRegister(registerReq);
    }
    /**
     * 忘记密码
     * @param newPwd 新密码
     *@param userId 用户id
     * @return
     */
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> forgetPassword(String newPwd,Integer userId,String phoneCaptch,String phone){
        Response<Object> response = new Response<>();
        response.setData(registerService.forgetPassword(newPwd,userId,phoneCaptch,phone));
        return response;
    }

    /**
     *获取手机验证码
     * @param phone 手机号
     * @return
     */
    @RequestMapping("/getPhoneCapth")
    @ResponseBody
    public Response<Object> getPhoneCapth( @RequestParam(value = "phone",required = false) String phone
                                        ){
        Response<Object> response = new Response<>();
        response.setData(registerService.getPhoneCapth(phone));
        return response;
    }
}
