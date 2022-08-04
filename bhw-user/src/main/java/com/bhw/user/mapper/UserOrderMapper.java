package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.PhoneRecord;
import com.bhw.user.vo.UserOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderMapper extends BaseMapper<UserOrderVo> {

    public IPage<UserOrderVo> qryUserOrderList(Page page, @Param("userOrderVo") UserOrderVo userOrderVo);

    public IPage<UserOrderVo> qryPayDealList(Page page, @Param("userOrderVo") UserOrderVo userOrderVo);

    public IPage<UserOrderVo> qryUserWalletOrderList(Page page, @Param("userOrderVo") UserOrderVo userOrderVo);

    public IPage<UserOrderVo> backQryWalletOrderList(Page page, @Param("userOrderVo") UserOrderVo userOrderVo);

    public IPage<UserOrderVo> backQryPayDealList(Page page, @Param("userOrderVo") UserOrderVo userOrderVo);

    public UserOrderVo qryUserWalletOrderList(@Param("userOrderVo") UserOrderVo userOrderVo);

    void upUserOrderStatus( @Param("userOrderVo")UserOrderVo userOrderVo);

    void upUserOrderProtocol(@Param("userOrderVo")UserOrderVo userOrderVo);

    public UserOrderVo getStUserWalletMy(@Param("userOrderVo") UserOrderVo userOrderVo);

}
