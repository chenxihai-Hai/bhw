package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.BrokerAdvisory;
import com.bhw.admin.pojo.ServerAdvisory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrokerAdvisoryMapper extends BaseMapper<BrokerAdvisory> {

    public IPage<BrokerAdvisory> selectBrokerAdvisoryList(Page page,@Param("brokerAdv")BrokerAdvisory brokerAdv);
}
