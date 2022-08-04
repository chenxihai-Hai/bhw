package com.bhw.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.mapper.BhwUserInitMapper;
import com.bhw.user.mapper.BhwUserMapper;
import com.bhw.user.mapper.PayMentAccoutMapper;
import com.bhw.user.mapper.PhoneRecordMapper;
import com.bhw.user.pojo.BhwUser;
import com.bhw.user.pojo.BhwUserInit;
import com.bhw.user.pojo.PayMentAccout;
import com.bhw.user.pojo.PhoneRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BhwUserService {


    @Resource
    private BhwUserMapper bhwUserMapper;

    @Resource
    private PhoneRecordMapper phoneRecordMapper;

    @Resource
    private BhwUserInitMapper bhwUserInitMapper;

    @Resource
    private PayMentAccoutMapper payMentAccoutMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<BhwUser> qryUser(BhwUser user, com.bhw.common.base.Page page){
        QueryWrapper<BhwUser> bhwUserQueryWrapper = new QueryWrapper<>(user);
        if(StringUtils.isNotEmpty(user.getCreateTimeSt())){
            bhwUserQueryWrapper.ge("createTime",user.getCreateTimeSt());
        }
        bhwUserQueryWrapper.orderByDesc("createTime");
        IPage<BhwUser> userMbBeanIPage = bhwUserMapper.selectPage(new Page<BhwUser>(page.getCurPage(), page.getPageSize()),bhwUserQueryWrapper);
       // IPage<BhwUser> userMbBeanIPage = bhwUserMapper.qryUser(new Page<BhwUser>(page.getCurPage(), page.getPageSize()));
        List<BhwUser> list = userMbBeanIPage.getRecords();
        page.setCounts((int)userMbBeanIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)userMbBeanIPage.getCurrent());
        return list;
    }

    public BhwUser qryBhwUserByPhone(String phone){
        Wrapper<BhwUser> queryWrapper =  new QueryWrapper<BhwUser>().eq("phoneNo",phone);
        return bhwUserMapper.selectOne(queryWrapper);
    }


    public void upBhwUser(BhwUser bhwUser){
        bhwUserMapper.updateById(bhwUser);
    }

    public void addBhwUser(BhwUser bhwUser){
        String passWord = bhwUser.getPassWord();
        bhwUser.setPassWord(encoder.encode(passWord));
        int userId = bhwUserMapper.insert(bhwUser);
        BhwUserInit bhwUserInit = new BhwUserInit();
        bhwUserInit.setUserId(userId);
        bhwUserInit.setPassWord(passWord);
        bhwUserInitMapper.insert(bhwUserInit);
    }

    public void upBhwUserPw(BhwUserInit userInit){
        bhwUserInitMapper.upUserInitPw(userInit);
    }

    public void upBhwUserPh(PhoneRecord phoneRecord){
        phoneRecordMapper.insert(phoneRecord);
    }

    public void upUserPayPw(PayMentAccout payMentAccout,String newPw){
        BhwUserInit bhwUserInit = new BhwUserInit();
        bhwUserInit.setUserId(payMentAccout.getUserId());
        if(null == payMentAccout.getId()){
            payMentAccout.setPayPassWord(newPw);
            payMentAccout.setStatus(1);
            bhwUserInit.setPayPassWord(newPw);
            bhwUserInitMapper.upUserInitPw(bhwUserInit);
            payMentAccoutMapper.insert(payMentAccout);
            return;
        }
        payMentAccout.setPayPassWord(newPw);
        payMentAccoutMapper.updateById(payMentAccout);
        bhwUserInit.setPayPassWord(newPw);
        bhwUserInitMapper.upUserInitPw(bhwUserInit);
    }
}
