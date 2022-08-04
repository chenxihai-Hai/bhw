package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.util.DateUtil;
import com.bhw.common.util.SmsUtil;
import com.bhw.common.util.ThirdpartyJhUtil;
import com.bhw.common.util.jwt.IJWTInfo;
import com.bhw.common.util.jwt.JWTHelper;
import com.bhw.common.util.jwt.JWTInfo;
import com.bhw.user.mapper.*;
import com.bhw.user.pojo.*;
import com.bhw.user.service.BhwUserService;
import com.bhw.user.vo.UserOrderVo;
import com.netflix.discovery.util.StringUtil;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/bhwUser")
public class BhwUserRest {
    Logger logger = LoggerFactory.getLogger(BhwUserRest.class);

    @Autowired
    private BhwUserService bhwUserService;

    @Resource
    private BhwUserMapper bhwUserMapper;

    @Resource
    private UserSubsidiayMapper userSubsidiayMapper;

    @Resource
    PayMentAccoutMapper payMentAccoutMapper;

    @Resource
    CompanyCardMapper companyCardMapper;

    @Resource
    UserMemberMapper userMemberMapper;

    @Resource
    UserOrderMapper userOrderMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Value("${account_par_key}")
    public String priKey;

    @RequestMapping("/qryUser")
    @ResponseBody
    public Response<Object> qryUserList(BhwUser user,Page page) {
        Response<Object> response = new Response<>();
        List<BhwUser> bhwUsers = bhwUserService.qryUser(user,page);
        response.setData(bhwUsers);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/getUserDetailByToken")
    @ResponseBody
    public Response<Object> getUserDetailByToken(String token){
        Response<Object> response = new Response<>();
        try {
            IJWTInfo infoFromToken = JWTHelper.getInfoFromToken(token, priKey);
            if(null != infoFromToken){
                JWTInfo info = (JWTInfo) infoFromToken;
                BhwUser bhwUser = bhwUserMapper.selectById(info.getId());
                response.setData(bhwUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRetCode(-1);
        }
        return response;
    }

    @RequestMapping("/getUserSubsInfo")
    @ResponseBody
    public Response<Object> getUserSubsInfo(UserSubsidiary userSubsidiary){
        Response<Object> response = new Response<>();
        UserSubsidiary userSubsidiary1 = userSubsidiayMapper.selectOne(new QueryWrapper<UserSubsidiary>(userSubsidiary));
        response.setData(userSubsidiary1);
        return response;
    }


    @RequestMapping("/getUserInfoByToken")
    @ResponseBody
    public Response<Object> getUserInfoByToken(String token){
        Response<Object> response = new Response<>();
        try {
            IJWTInfo infoFromToken = JWTHelper.getInfoFromToken(token, priKey);
            if(null != infoFromToken){
                response.setData((JWTInfo) infoFromToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRetCode(-1);
        }
        return response;
    }

    @RequestMapping("/upBhwUser")
    @ResponseBody
    public Response<Object> upBhwUser(BhwUser bhwUser,UserSubsidiary userSubsidiary) {
        Response<Object> response = new Response<>();
        bhwUserService.upBhwUser(bhwUser);
        if(null != bhwUser.getType() && 1==bhwUser.getType()){
            Integer subId = bhwUser.getSubId();
            if(null == subId){
                userSubsidiayMapper.insert(userSubsidiary);
            }else{
                userSubsidiary.setId(subId);
                userSubsidiayMapper.updateById(userSubsidiary);
            }
        }
        return response;
    }

    @RequestMapping("/addBhwUser")
    @ResponseBody
    public Response<Object> addBhwUser(BhwUser bhwUser) {
        Response<Object> response = new Response<>();
        bhwUserService.addBhwUser(bhwUser);
        return response;
    }

    /**
     * 修改登录密码
     */
    @RequestMapping("/upUserPw")
    @ResponseBody
    public Response<Object> upUserPw(BhwUser user, String newPw,String oldPw) {
        Response<Object> response = new Response<>();
        BhwUser qr = new BhwUser();
        qr.setAccount(user.getAccount());
//        qr.setPassWord(encoder.encode(oldPw));
        BhwUser bhwUser = bhwUserMapper.selectOne(new QueryWrapper<BhwUser>(qr));
        if(null == bhwUser || null == bhwUser.getId()){
            response.setRetCode(-1);
            return response;
        }
        if(!encoder.matches(oldPw,bhwUser.getPassWord())){
            response.setRetCode(-1);
            return response;
        }
        user.setPassWord(encoder.encode(newPw));
        user.setId(bhwUser.getId());
        bhwUserService.upBhwUser(user);
        BhwUserInit bhwUserInit = new BhwUserInit();
        bhwUserInit.setUserId(bhwUser.getId());
        bhwUserInit.setPassWord(newPw);
        bhwUserService.upBhwUserPw(bhwUserInit);
        return response;
    }

    /**
     * 更换手机号
     */
    @RequestMapping("/upUserPhone")
    @ResponseBody
    public Response<Object> upUserPhone(BhwUser bhwUser, PhoneRecord phoneRecord) {
        Response<Object> response = new Response<>();
        bhwUser.setPhoneNo(phoneRecord.getNewPhone());
        bhwUserService.upBhwUser(bhwUser);
        phoneRecord.setUserId(bhwUser.getId());
        phoneRecord.setId(null);
        bhwUserService.upBhwUserPh(phoneRecord);
        return response;
    }

    /**
     * 修改支付密码
     */
    @RequestMapping("/upUserPayPw")
    @ResponseBody
    public Response<Object> upUserPayPw(PayMentAccout payMentAccout, String newPw,String oldPw) {
        Response<Object> response = new Response<>();
        PayMentAccout qr = new PayMentAccout();
        qr.setUserId(payMentAccout.getUserId());
        PayMentAccout payMentAccout1 = payMentAccoutMapper.selectOne(new QueryWrapper<PayMentAccout>(qr));
        if(null != payMentAccout1 && StringUtils.isEmpty(payMentAccout1.getPayPassWord())){
            payMentAccout.setId(payMentAccout1.getId());
            bhwUserService.upUserPayPw(payMentAccout, newPw);
            return response;
        }
        if(null != payMentAccout1 && StringUtils.isNotEmpty(payMentAccout1.getPayPassWord()) && !payMentAccout1.getPayPassWord().equals(oldPw)){
            response.setRetCode(-1);
            return response;
        }
        payMentAccout.setId(payMentAccout1.getId());
        bhwUserService.upUserPayPw(payMentAccout, newPw);
        return response;
    }

    /**
     * 个人实名认证
     * */
    @RequestMapping("/userIdCardverify")
    @ResponseBody
    public Response<Object> userIdCardverify(BhwUser bhwUser){
        Response<Object> response = new Response<>();
        int i = ThirdpartyJhUtil.idcardVerify(bhwUser.getUserName(), bhwUser.getIdCard());
        if(i==2 || i==0){
            response.setRetCode(-1);
        }
        bhwUser.setStatus(1);
        bhwUserService.upBhwUser(bhwUser);
        return response;
    }

    /**
     * 企业实名认证
     * */
    @RequestMapping("/compayVerify")
    @ResponseBody
    public Response<Object> compayVerify(BhwCompanyCard bhwCompanyCard,String userName){
        Response<Object> response = new Response<>();
        int i = ThirdpartyJhUtil.companyVerify(bhwCompanyCard.getUniteCode(), userName, bhwCompanyCard.getOpername());
        if(i!=1){
            response.setRetCode(-1);
        }
        BhwUser bhwUser = new BhwUser();
        bhwUser.setId(bhwCompanyCard.getUserId());
        bhwUser.setStatus(1);
        bhwUserService.upBhwUser(bhwUser);
        BhwCompanyCard bhwCompanyCard1 = new BhwCompanyCard();
        bhwCompanyCard1.setUserId(bhwCompanyCard.getUserId());
        Wrapper<BhwCompanyCard> queryWrapper =  new QueryWrapper<BhwCompanyCard>(bhwCompanyCard1);
        companyCardMapper.update(bhwCompanyCard,queryWrapper);
        return response;
    }

    @RequestMapping("/getBhwCompanyCardInfo")
    @ResponseBody
    public Response<Object> getBhwCompanyCardInfo(BhwCompanyCard bhwCompanyCard){
        Response<Object> response = new Response<>();
        response.setData(companyCardMapper.selectOne(new QueryWrapper<>(bhwCompanyCard)));
        return response;
    }

    @RequestMapping("/upBhwCompanyCardInfo")
    @ResponseBody
    public Response<Object> upBhwCompanyCardInfo(BhwCompanyCard bhwCompanyCard){
        Response<Object> response = new Response<>();
        response.setData(companyCardMapper.updateById(bhwCompanyCard));
        return response;
    }

    @RequestMapping("/saveUserMember")
    @ResponseBody
    public Response<Object> saveUserMember(UserMember userMember){
        Response<Object> response = new Response<>();
        UserMember um = new UserMember();
        um.setUserId(userMember.getUserId());
        UserMember userMember1 = userMemberMapper.selectOne(new QueryWrapper<>(um));
        if(null == userMember1 || null == userMember1.getId()){
            userMemberMapper.insert(userMember);
            return response;
        }
        userMember.setId(userMember1.getId());
        userMemberMapper.updateById(userMember);
        return response;
    }

    @RequestMapping("/upUserMemberByPay")
    @ResponseBody
    public Response<Object> upUserMemberByPay(UserMember userMember){
        Response<Object> response = new Response<>();
        UserMember userMember1 = userMemberMapper.selectOne(new QueryWrapper<>(userMember));
        if(null != userMember1 && !"-1".equals(userMember1.getLastOrderId())){
            UserOrderVo userOrderVo = new UserOrderVo();
            userOrderVo.setUserId(userMember.getUserId());
            userOrderVo.setOrderId(userMember1.getLastOrderId());
            UserOrderVo userOrderVo1 = userOrderMapper.qryUserWalletOrderList(userOrderVo);
            if(userOrderVo1.getPayStatus()==2){
                String indate1 = userMember1.getIndate();
                if(StringUtils.isEmpty(indate1)){
                    indate1 = DateUtil.getCurrentTime();
                    String indate = indate1.split(" ")[0].substring(0,4);
                    String newIndate = (Integer.valueOf(indate)+1)+"";
                    indate1 = newIndate+indate1.substring(4);
                }else{
                    String indate = userMember1.getIndate().split(" ")[0].substring(0,4);
                    String newIndate = (Integer.valueOf(indate)+1)+"";
                    indate1 = newIndate+userMember1.getIndate().substring(4);
                }
                userMember.setIndate(indate1);
                userMember.setStatus(1);
                userMember.setId(userMember1.getId());
                userMember.setLastOrderId("-1");
                userMemberMapper.updateById(userMember);
            }
        }
        return response;
    }

    @RequestMapping("/pushUserMemberMsg")
    @ResponseBody
    public Response<Object> pushUserMemberMsg(String phone,String msg){
        Response<Object> response = new Response<>();
        int flag= SmsUtil.sendSmsSecurityCode(phone, "【北部湾中心】尊敬的用户,"+msg);
        response.setData(flag);
        return response;
    }

    @RequestMapping("/getUserMember")
    @ResponseBody
    public Response<Object> getUserMember(UserMember userMember){
        Response<Object> response = new Response<>();
        UserMember userMember1 = userMemberMapper.selectOne(new QueryWrapper<>(userMember));
        response.setData(userMember1);
        return response;
    }

    @RequestMapping("/qryUserMemberList")
    @ResponseBody
    public Response<Object> qryUserMemberList(UserMember userMember,Page page){
        Response<Object> response = new Response<>();
        IPage<UserMember> qryUserMemberList = userMemberMapper.qryUserMemberList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserMember>(page.getCurPage(), page.getPageSize()), userMember);
        List<UserMember> records = qryUserMemberList.getRecords();
        page.setCounts((int)qryUserMemberList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryUserMemberList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }
}
