package com.bhw.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.common.util.CaptchaUtil;
import com.bhw.common.util.SmsUtil;
import com.bhw.user.dto.EnterpriseRegisterReq;
import com.bhw.user.dto.PersonalRegisterReq;
import com.bhw.user.mapper.BhwUserInitMapper;
import com.bhw.user.mapper.BhwUserMapper;
import com.bhw.user.mapper.CompanyCardMapper;
import com.bhw.user.mapper.IncreMentServerMapper;
import com.bhw.user.pojo.BhwCompanyCard;
import com.bhw.user.pojo.BhwUser;
import com.bhw.user.pojo.BhwUserInit;
import com.bhw.user.pojo.IncreMentServer;
import com.bhw.user.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@Transactional
public class RegisterService {
//    private static Map<String,String> mapCach=new HashMap<>();

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Resource
    private BhwUserMapper bhwUserMapper;
    @Resource
    private IncreMentServerMapper increMentServerMapper;

    @Resource
    private BhwUserInitMapper bhwUserInitMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CompanyCardMapper companyCardMapper;
    public String captchaOutput(String randomCode, HttpServletResponse respons)throws Exception{
        String vCode= CaptchaUtil.captchaOutput(respons);
        // 保存到redis，有效时间5分钟
       // redisClient.setex(0, CommonConstant.CAPTCHA_REDIS_KEY_PREFIX + randomCode, CommonConstant.CAPTCHA_REDIS_TIME_OUT, vCode);
        redisUtil.set(randomCode,vCode,300);
        System.out.println("captchaOutput"+"-----randomCode:"+randomCode+"---vCode:"+vCode);
        return vCode;
    }
    public boolean captchaCheck(String randomCode,String vCode,String phone){
        redisUtil.set("1234","1234");//演示测试
        String value=redisUtil.get(randomCode);
        System.out.println("captchaCheck"+"-----randomCode:"+randomCode+"---vCode:"+vCode+"---value:"+value);
        redisUtil.del(randomCode);
        if(vCode.replaceAll("\\s*","").equals(value)){
            String random = SmsUtil.getRandom(4);
           // random = "8888";
            System.out.println("手机验证码:"+random);
            int flag= SmsUtil.sendSmsSecurityCode(phone, "【北部湾中心】"+random+"(动态验证码，5分钟内有效)，请妥善保存此验证码，以免泄露。如非本人操作，请忽略。");
            if(flag==0){
                redisUtil.set(phone,random,300);
            }
            return true;
        }
        return false;
    }

    public boolean viCaptchaCheckByCode(String vCode,String phone){
        String code = redisUtil.get(phone);
        return StringUtils.equals(code,phone);
    }

    public void personalRegister(PersonalRegisterReq req,Response<Object> response){
            //缓存中获取的手机验证码
            String checkPhoneCaptcha=redisUtil.get(req.getPhone());
            redisUtil.del(req.getPhone());
            if(!req.getPhoneCaptcha().equals(checkPhoneCaptcha)){
                response.setRetCode(1);
                response.setMessage("验证码错误");
                return;
            }
            BhwUser bhwUser=bhwUserMapper.selectByPhone(req.getPhone());
            if(bhwUser!=null){
                response.setRetCode(1);
                response.setMessage("已注册用户");
                return;
            }
            BhwUser insertUser=new BhwUser();
            insertUser.setAccount(req.getPhone());
            insertUser.setPhoneNo(req.getPhone());
            insertUser.setPassWord(encoder.encode(req.getPwd()));//存密文
            insertUser.setCreateTime(new Date());
            insertUser.setStatus(2);//未实名
            int flag= bhwUserMapper.insert(insertUser);
            if(flag>0){
                Integer userId=bhwUserMapper.selectIdByPhone(req.getPhone());
                BhwUserInit bhwUserInit=new BhwUserInit();
                bhwUserInit.setUserId(userId);
                bhwUserInit.setPassWord(req.getPwd());
                bhwUserInit.setCreateTime(new Date());
                bhwUserInitMapper.insertUserInit(bhwUserInit);
                List<IncreMentServer>  serverList=new ArrayList<>();
                response.setRetCode(0);
                response.setMessage("注册成功");
                return;
            }
             response.setRetCode(1);
             response.setMessage("注册失败");
             return ;
    }
    public  Response<Object> enterpriseRegister(EnterpriseRegisterReq registerReq){
        Response<Object> response = new Response<>();
        //缓存中获取的手机验证码
        String checkPhoneCaptcha=redisUtil.get(registerReq.getContactPhone());
        if(!registerReq.phoneCaptcha.equals(checkPhoneCaptcha)){
            response.setRetCode(1);
            response.setMessage("验证码错误");
            return response;
        }
        BhwUser qryBu = new BhwUser();
        qryBu.setAccount(registerReq.getAccount());
        BhwUser bhwUser = bhwUserMapper.selectOne(new QueryWrapper<>(qryBu));
//        BhwUser bhwUser=bhwUserMapper.selectByUserName(registerReq.companyName);
        if(bhwUser!=null && null != bhwUser.getId()){
            response.setRetCode(1);
            response.setMessage("该登录账号已注册");
            return response;
        }
        BhwUser insertUser=new BhwUser();
        insertUser.setUserName(registerReq.companyName);
        insertUser.setAccount(registerReq.getAccount());
        insertUser.setPhoneNo(registerReq.companyPhone);
        insertUser.setPassWord(encoder.encode(registerReq.pwd));//存密文
        insertUser.setCreateTime(new Date());
        insertUser.setStatus(2);//未实名
        insertUser.setType(2);
        int insert = bhwUserMapper.insert(insertUser);
//        boolean flag= bhwUserMapper.insertSelective(insertUser);
        if(insert>0){
            Integer userId=insertUser.getId();
            BhwUserInit bhwUserInit=new BhwUserInit();
            bhwUserInit.setUserId(userId);
            bhwUserInit.setPassWord(registerReq.pwd);
            bhwUserInit.setCreateTime(new Date());
            bhwUserInitMapper.insertUserInit(bhwUserInit);
            //保存营业执照等信息
            BhwCompanyCard record=new BhwCompanyCard();
            record.setUserId(userId);
            record.setUniteCode(registerReq.uniteCode);
            record.setContactPhone(registerReq.contactPhone);
            record.setBusinessLicense(registerReq.businessLicense);
            record.setAgentCode(registerReq.agencyCode);
            record.setTaxRegisterCode(registerReq.taxRegisterCode);
            record.setType(registerReq.type);
            record.setCompanyName(registerReq.companyName);
            companyCardMapper.insertSelective(record);
            response.setRetCode(0);
            response.setMessage("注册成功");
            return response;
        }
        response.setRetCode(1);
        response.setMessage("注册失败");
        return response;
    }

    public boolean forgetPassword(String newPwd,Integer userId,String phoneCaptch,String phone){
        boolean flag=false;
        String checkPhoneCaptcha=redisUtil.get(phone);
        redisUtil.del(phone);
        if(!phoneCaptch.equals(checkPhoneCaptcha)){
            return false;
        }
        BhwUser bhwUser=new BhwUser();
        bhwUser.setPassWord(encoder.encode(newPwd));
        bhwUser.setId(userId);
        bhwUserMapper.updateById(bhwUser);
        BhwUserInit bhwUserInit=new BhwUserInit();
        bhwUserInit.setPassWord(newPwd);
        bhwUserInit.setUserId(userId);
        bhwUserInitMapper.upUserInitPw(bhwUserInit);
        return true;
    }

    public boolean getPhoneCapth(String phone){
        boolean flag1=false;
        String random = SmsUtil.getRandom(4);
        System.out.println("手机验证码:"+random);
        int flag= SmsUtil.sendSmsSecurityCode(phone, "【北部湾中心】"+random+"(动态验证码，5分钟内有效)，请妥善保存此验证码，以免泄露。如非本人操作，请忽略。");
        if(flag==0){
            redisUtil.set(phone,random,300);
            flag1=true;
        }
        return flag1;
    }
}
