package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.BrokerAdvisory;
import com.bhw.admin.pojo.BrokerLeaveMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrokerLeaveMsgMapper extends BaseMapper<BrokerLeaveMsg> {

}
