package com.bhw.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.pay.pojo.OrderUser;
import com.bhw.pay.pojo.WalletOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletOrderMapper extends BaseMapper<WalletOrder> {

}
