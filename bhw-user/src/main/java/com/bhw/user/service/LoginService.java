package com.bhw.user.service;

import com.bhw.common.util.jwt.JWTHelper;
import com.bhw.common.util.jwt.JWTInfo;
import com.bhw.user.mapper.BhwUserMapper;
import com.bhw.user.pojo.BhwUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RefreshScope
@Service
@Transactional
public class LoginService {
    @Resource
    BhwUserMapper bhwUserMapper;

    @Value("${account_par_key}")
    public String priKey;

    @Value("${account_par_key_expire}")
    public String expire;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String userLogin(String account,String phone,String pwd){
        BhwUser user =bhwUserMapper.checkPwd(account,phone);
        if(null == user){
            return "";
        }
        if(encoder.matches(pwd,user.getPassWord())){
            try {
                String token = JWTHelper.generateToken(new JWTInfo(user.getUserName(), user.getId() + "", phone),priKey,Integer.valueOf(expire));
                return token;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public BhwUser getUserInfo(String username,String phone){
        BhwUser user =bhwUserMapper.checkPwd(username,phone);
        return user;
    }


//    public void  getbackPwd(String phone, String phoneCaptcha,Response<Object> response){
////        //从redis中获取手机验证码和输入进行比较
////        String checkCaptcha="1234";
////        if(!phoneCaptcha.equals(checkCaptcha)){
////            response.setRetCode(1);
////            response.setMessage("验证码错误");
////            return;
////        }
////        BhwUser bhwUser=bhwUserMapper.selectByPhone(phone);
////        if(bhwUser!=null){
////            String pwd=bhwUserMapper.selectPwdByPhone(phone);
////            response.setRetCode(0);
////            response.setData(pwd);
////            response.setMessage("找回密码成功");
////            return;
////        }
////        response.setRetCode(2);
////        response.setMessage("用户不存在");
////        return;
////    }
}
