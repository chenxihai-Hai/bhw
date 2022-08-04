package com.bhw.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.pay.pojo.OrderBean;
import com.bhw.pay.pojo.OrderUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderUserMapper extends BaseMapper<OrderUser> {

}
