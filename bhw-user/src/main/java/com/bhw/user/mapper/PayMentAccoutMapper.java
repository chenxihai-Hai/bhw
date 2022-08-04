package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.user.pojo.PayMentAccout;
import com.bhw.user.vo.PayAccountPwdVo;
import com.bhw.user.vo.PayAccountVerifyVo;
import com.bhw.user.vo.PayBankCardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface PayMentAccoutMapper extends BaseMapper<PayMentAccout> {

    public boolean updateAccountPassword(PayAccountPwdVo vo);

    public int selectAccountPresence(PayAccountVerifyVo verifyVo);

    public int selecttAccountStatusByUserId(Integer userid);

    public boolean updatePwdByUserId(@Param("map") Map params);

    public boolean updateAccountPhone(@Param("map") Map params);

    public int selectByPhoneAndUserid(@Param("map") Map params);

    public int insertNewBankCard(PayBankCardVo verifyVo);
}
