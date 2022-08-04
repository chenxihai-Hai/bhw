package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.UserWallet;
import com.bhw.user.pojo.UserWithdraw;
import com.bhw.user.vo.UserOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserWIthdrawMapper extends BaseMapper<UserWithdraw> {

    public IPage<UserWithdraw> qryUserWithdrawList(Page page, @Param("userWithdraw") UserWithdraw userWithdraw);
}
