package com.bhw.bhwtechology.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.bhwtechology.pojo.ServerLeaveMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ServerLeaveMsgMapper extends BaseMapper<ServerLeaveMsg> {
    List<ServerLeaveMsg> selectByTypeAndServerId(@Param("map") Map<String, Object> map);
}
