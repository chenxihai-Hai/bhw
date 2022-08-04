package com.bhw.fiscalTax.mapper;

import com.bhw.fiscalTax.pojo.ServerLeaveMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ServerLeaveMsgMapper {
    List<ServerLeaveMsg> selectByTypeAndServerId(@Param("map") Map<String,Object> map);
}
