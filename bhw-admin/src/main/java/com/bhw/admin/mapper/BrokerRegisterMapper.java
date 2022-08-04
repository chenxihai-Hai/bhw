package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.BrokerAdvisory;
import com.bhw.admin.pojo.BrokerRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrokerRegisterMapper extends BaseMapper<BrokerRegister> {

    public IPage<BrokerRegister> qryBrokerList(Page page, @Param("broker")BrokerRegister brokerRegister);

    public IPage<BrokerRegister> backQryBrokerList(Page page, @Param("broker")BrokerRegister brokerRegister);
}
