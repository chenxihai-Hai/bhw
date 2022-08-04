package com.bhw.techSupply.service;


import com.bhw.techSupply.mapper.ServerLeaveMsgMapper;
import com.bhw.techSupply.pojo.ServerLeaveMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LeaveMgService {
    @Autowired
    ServerLeaveMsgMapper serverLeaveMsgMapper;
    public List<ServerLeaveMsg> getServerLeaveMsg(Integer type, Integer serverId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("type",type);
        map.put("serverId",serverId);
        List<ServerLeaveMsg> list=serverLeaveMsgMapper.selectByTypeAndServerId(map);
        return list;
    }
}
