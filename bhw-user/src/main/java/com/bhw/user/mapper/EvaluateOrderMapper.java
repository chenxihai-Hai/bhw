package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.AddressManage;
import com.bhw.user.pojo.EvaluateOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EvaluateOrderMapper extends BaseMapper<EvaluateOrder> {

    IPage<EvaluateOrder> qryEvaluateOrderList(Page page, @Param("evaluateOrder") EvaluateOrder evaluateOrder);

    EvaluateOrder statisticsEvaluate(@Param("evaluateOrder") EvaluateOrder evaluateOrder);
}
