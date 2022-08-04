package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.user.pojo.PayMentAccout;
import com.bhw.user.pojo.PayMentBankCard;
import com.bhw.user.vo.PayAccountPwdVo;
import com.bhw.user.vo.PayAccountVerifyVo;
import com.bhw.user.vo.PayBankCardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface PayMentBankCardMapper extends BaseMapper<PayMentBankCard> {

}
