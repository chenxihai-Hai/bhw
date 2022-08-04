package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.PayMentAccoutMapper;
import com.bhw.user.pojo.PayMentAccout;
import com.bhw.user.service.AccountMgrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RefreshScope
@RequestMapping("/payAccountMsg")
public class AccountMgRest {
    private final static Logger logger = LoggerFactory.getLogger(AccountMgRest.class);

    @Autowired
    private AccountMgrService accountMgrService;

    @Resource
    private PayMentAccoutMapper payMentAccoutMapper;

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
            String vCode = accountMgrService.captchaOutput(randomCode, response);
            logger.info("accountMgrService.captchaOutput({}):{}", randomCode, vCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/captchaVi", method = RequestMethod.GET)
    public Response<Object> captchaVi(String randomCode){
        Response<Object> response=new Response<Object>();
        try {
            String vCode = accountMgrService.getCaptchaOutput(randomCode);
            response.setData(vCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * 获取手机验证码
     * @param phone
     */
    @RequestMapping(value = "/phoneCaptcha", method = RequestMethod.GET)
    public Response<Object> Phonecaptcha(@RequestParam(value = "phone",required = true) String phone){
        Response<Object> response=new Response<Object>();
        boolean flag=false;
        try {
            String vCode = accountMgrService.phoneCaptcha(phone);
            if(!"".equals(vCode)){
                flag=true;
            }
            logger.info("accountMgrService.phoneCaptcha({}):{}", phone, vCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        response.setData(flag);
        return response;
    }

    /**
     * 手机号修改申诉短信验证
     * @param phone
     * @param phoneCaptcha
     * @return
     */
    @RequestMapping(value = "/verifyPhonecaptcha", method = RequestMethod.POST)
    public Response<Object> verifyPhonecaptcha(@RequestParam(value = "phone",required = true) String phone,
                                               @RequestParam(value = "phoneCaptcha",required = true) String phoneCaptcha,
                                               @RequestParam(value = "userid",required = true) String userid){
        Response<Object> response=new Response<Object>();
        response.setData(accountMgrService.verifyPhonecaptcha(phone,phoneCaptcha,userid));
        return response;
    }
    /**
     * 查询账号状态
     * @param userid  用户id
     */
    @RequestMapping(value = "/getAccountStatus", method = RequestMethod.GET)
    public Response<Object> getAccountStatus(@RequestParam(value = "userid",required = true) Integer userid){
        Response<Object> response=new Response<Object>();
        PayMentAccout qr = new PayMentAccout();
        qr.setUserId(userid);
        PayMentAccout payMentAccout1 = payMentAccoutMapper.selectOne(new QueryWrapper<PayMentAccout>(qr));
        if(null == payMentAccout1 || null == payMentAccout1.getId()){
            payMentAccoutMapper.insert(qr);
            response.setData(0);
            return response;
        }
        response.setData(payMentAccout1.getStatus());
        response.setMessage(payMentAccout1.getId().toString());
        return response;
    }


    /**
     * 重置支付密码
     * @param pwd
     * @param userid
     * @param randomCode
     * @param vCode
     * @return
     */
    @RequestMapping(value = "/setPayPwd", method = RequestMethod.POST)
    public Response<Object> setPayPwd(@RequestParam(value = "pwd",required = true) String pwd,
                                      @RequestParam(value = "userid",required = true) Integer userid,
                                      @RequestParam(value = "randomCode",required = true) String randomCode,
                                      @RequestParam(value = "vCode",required = true) String vCode){
        Response<Object> response=new Response<Object>();
        response.setData(accountMgrService.setPayPwd(pwd,userid,randomCode,vCode));
        return response;
    }
    /**
     * 修改密码验证身份
     * 通过银行卡及手机号(bankType,bankCard,idCard不为空) type=2
     * 通过原始密码及手机号（oldPassword不为空） type=1
     * @param phone 手机号
     * @param captcha 图形验证码
     * @param phoneCaptcha 手机验证码
     * @return
     */
    @RequestMapping(value = "/verifyIdentity", method = RequestMethod.POST)
    public Response<Object> verifyIdentity(@RequestParam(value = "phone",required = true) String phone,
                                           @RequestParam(value = "type",required = true) String type,
                                           @RequestParam(value = "userId",required = true) String userId,
                                           @RequestParam(value = "randomCode",required = true) String randomCode,
                                           @RequestParam(value = "captcha",required = true) String captcha,
                                           @RequestParam(value = "phoneCaptcha",required = true) String phoneCaptcha,
                                           @RequestParam(value = "oldPassword",required = false) String oldPassword,
                                           @RequestParam(value = "bankCard",required = false) String bankCard,
                                           @RequestParam(value = "idCard",required = false) String idCard){
        Response<Object> response=new Response<Object>();
        response.setData(accountMgrService.verifyIdentity(phone,type,randomCode,captcha,phoneCaptcha,oldPassword,bankCard,idCard,userId));
        return response;
    }

    /**
     * 账号申诉身份验证
     * @param phone
     * @param type (1手机验证码验证，2银行卡验证，3新银行卡验证)
     * @param phoneCaptcha
     * @param bankCard
     * @param idCard
     * @return
     */
    @RequestMapping(value = "/accountAppealverify", method = RequestMethod.POST)
    public Response<Object> accountAppealVerify(@RequestParam(value = "phone",required = false) String phone,
                                                 @RequestParam(value = "type",required = true) String type,
                                                 @RequestParam(value = "userId",required = true) String userId,
                                                 @RequestParam(value = "phoneCaptcha",required = false) String phoneCaptcha,
                                                 @RequestParam(value = "bankCard",required = false) String bankCard,
                                                 @RequestParam(value = "idCard",required = false) String idCard){

        Response<Object> response=new Response<Object>();
        boolean flag=accountMgrService.accountAppealVerify(phone,type,phoneCaptcha,bankCard,idCard,userId);
        response.setData(flag);
        return response;
    }

    /**
     * 登录密码修改申诉
     * @param phone
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/accountPwdAppeal", method = RequestMethod.POST)
    public Response<Object> accountPwdAppeal(@RequestParam(value = "phone",required = false) String phone,
                                             @RequestParam(value = "newPassword",required = false) String newPassword,
                                             @RequestParam(value = "userId",required = true) String userId){
        Response<Object> response=new Response<Object>();
        boolean flag=accountMgrService.accountPwdAppeal(phone,newPassword,userId);
        response.setData(flag);
        return response;
    }

    /**
     * 手机号修改申诉
     */
    @RequestMapping(value = "/accountPhoneAppeal", method = RequestMethod.POST)
    public Response<Object> accountPhoneAppeal(@RequestParam(value = "phone",required = true) String phone,
                                               @RequestParam(value = "userId",required = true) String userId){
        Response<Object> response=new Response<Object>();
        boolean flag=accountMgrService.accountPhoneAppeal(phone,userId);
        response.setData(flag);
        return response;
    }

}
