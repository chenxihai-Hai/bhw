package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.BrokerEvaluate;
import com.bhw.admin.pojo.BrokerRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrokerEvaluateMapper extends BaseMapper<BrokerEvaluate> {

}

