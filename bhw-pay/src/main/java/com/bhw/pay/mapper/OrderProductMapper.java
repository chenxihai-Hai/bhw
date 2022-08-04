package com.bhw.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.pay.pojo.OrderProduct;
import com.bhw.pay.pojo.OrderUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderProductMapper extends BaseMapper<OrderProduct> {

}
