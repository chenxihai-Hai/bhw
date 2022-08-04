package com.bhw.user.service;

import com.bhw.common.util.BankUtil;
import com.bhw.common.util.CaptchaUtil;
import com.bhw.common.util.SmsUtil;
import com.bhw.user.mapper.PayMentAccoutMapper;
import com.bhw.user.util.RedisUtil;
import com.bhw.user.vo.PayAccountPwdVo;
import com.bhw.user.vo.PayAccountVerifyVo;
import com.bhw.user.vo.PayBankCardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AccountMgrService {
//    private static Map<String,String> mapCach=new HashMap<>();

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private PayMentAccoutMapper payMentAccoutMapper;

    public String captchaOutput(String randomCode, HttpServletResponse respons)throws Exception{
        String vCode= CaptchaUtil.captchaOutput(respons);
//        mapCach.put(randomCode,vCode);
        redisUtil.set(randomCode,vCode,300);
        System.out.println("captchaOutput"+"-----randomCode:"+randomCode+"---vCode:"+vCode);
        return vCode;
    }

    public String getCaptchaOutput(String randomCode){
        return redisUtil.get(randomCode);
    }

    public String phoneCaptcha(String phone){
        Pattern p=Pattern.compile("(13|14|15|17|18|19)[0-9]{9}");
        Matcher m = p.matcher(phone);
        if(m.matches()){
            String random = SmsUtil.getRandom(4);
            int flag= SmsUtil.sendSmsSecurityCode(phone, "【北部湾中心】"+random+"(动态验证码，5分钟内有效)，请妥善保存此验证码，以免泄露。如非本人操作，请忽略。");
            if(flag==0){
//                mapCach.put(phone,random);
                redisUtil.set(phone,random,300);
                return random;
            }
        }
        return "";
    }
    public boolean verifyPhonecaptcha(String phone,String phoneCaptcha,String userid){
        boolean flag=false;
//        String mapCachPhoneCaptcha=mapCach.get(phone);
        String mapCachPhoneCaptcha = redisUtil.get(phone);
        if (mapCachPhoneCaptcha!=null && mapCachPhoneCaptcha.equals(phoneCaptcha)){
            flag = true;
            Map<String,Object> paramsMap=new HashMap<>();
            paramsMap.put("phone",phone);
            paramsMap.put("userid",userid);
            int count=payMentAccoutMapper.selectByPhoneAndUserid(paramsMap);
            System.out.println("count:"+count);
            if(count>0){
                flag=true;
            }
        }
        return flag;
    }
    public int getAccountStatus(Integer userid){
        return payMentAccoutMapper.selecttAccountStatusByUserId(userid);
    }

    public boolean setPayPwd(String pwd,Integer userid,String randomCode,String vCode){
//        String mapCachCaptcha=mapCach.get(randomCode);
        String mapCachCaptcha=redisUtil.get(randomCode);
        if(mapCachCaptcha!=null && mapCachCaptcha.equals(vCode)){
            Map<String,Object> paramsMap=new HashMap<>();
            paramsMap.put("userid",userid);
            paramsMap.put("pwd",pwd);
            return payMentAccoutMapper.updatePwdByUserId(paramsMap);
        }
        return false;
    }
    public boolean verifyIdentity(String phone,String type, String randomCode,String captcha,String phoneCaptcha,String oldPassword,
                                   String bankCard,String idCard,String userId){
        String mapCachPhoneCaptcha=redisUtil.get(phone);
        if (mapCachPhoneCaptcha!=null && mapCachPhoneCaptcha.equals(phoneCaptcha)){
            String mapCachCaptcha=redisUtil.get(randomCode);
            if(mapCachCaptcha!=null && mapCachCaptcha.equals(captcha)){
                PayAccountVerifyVo verifyVo=new PayAccountVerifyVo();
                verifyVo.setPhone(phone);
                verifyVo.setUserId(userId);
                if("1".equals(type)){
                    verifyVo.setOldPassword(oldPassword);
                }else {
                    verifyVo.setBankCard(bankCard);
                    verifyVo.setIdCard(idCard);
                }
                int count=payMentAccoutMapper.selectAccountPresence(verifyVo);
                if (count>0){
                     return true;
                }
            }
        }
        return false;
    }


    public boolean accountAppealVerify(String phone,String type,String phoneCaptcha,String bankCard,String idCard,String userId){
        boolean flag=false;
        if("1".equals(type)){
            String mapCachPhoneCaptcha=redisUtil.get(phone);
            if(mapCachPhoneCaptcha.equals(phoneCaptcha)){
                flag=true;
            }
        }else if("2".equals(type)){
            PayAccountVerifyVo verifyVo=new PayAccountVerifyVo();
            verifyVo.setUserId(userId);
            verifyVo.setBankCard(bankCard);
            verifyVo.setIdCard(idCard);
            int count=payMentAccoutMapper.selectAccountPresence(verifyVo);
            if (count>0){
                flag=true;
            }
        }else {
            String mapCachPhoneCaptcha=redisUtil.get(phone);
            if(mapCachPhoneCaptcha.equals(phoneCaptcha)){
                PayBankCardVo verifyVo=new PayBankCardVo();
                verifyVo.setBankCardType(1);//默认信用卡
                verifyVo.setBankCard(bankCard);
                verifyVo.setIdCard(idCard);
                String bankNameCode=BankUtil.getCardDetail(bankCard);
                if(!"".equals(bankNameCode)){
                    verifyVo.setBankName(bankNameCode);
                    int count=payMentAccoutMapper.insertNewBankCard(verifyVo);
                    if (count>0){
                        flag=true;
                    }
                }
            }
        }
        return flag;
    }


    public boolean accountPwdAppeal(String phone,String newPassword,String userId){
        PayAccountPwdVo vo = new PayAccountPwdVo();
        vo.setPhone(phone);
        vo.setNewPassword(newPassword);
        vo.setUserId(userId);
       return payMentAccoutMapper.updateAccountPassword(vo);
    }

    public boolean accountPhoneAppeal(String phone,String userId){
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("userid",userId);
        paramsMap.put("phone",phone);
        return payMentAccoutMapper.updateAccountPhone(paramsMap);
    }
}
