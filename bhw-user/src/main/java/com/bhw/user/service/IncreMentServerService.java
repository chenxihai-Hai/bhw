package com.bhw.user.service;

import com.bhw.user.mapper.IncreMentServerMapper;
import com.bhw.user.mapper.IncreMentServerRecordMapper;
import com.bhw.user.pojo.IncreMentServer;
import com.bhw.user.pojo.IncreMentServerRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class IncreMentServerService {


    @Resource
    private IncreMentServerMapper increMentServerMapper;

    @Resource
    private IncreMentServerRecordMapper increMentServerRecordMapper;

    public void addIncreMentServer(IncreMentServer incrementServer){
        increMentServerMapper.insert(incrementServer);
    }

    public void renewIncreMentServer(IncreMentServer incrementServer){
        IncreMentServerRecord increMentServerRecord = new IncreMentServerRecord();
        increMentServerRecord.setIncrementId(incrementServer.getId());
        increMentServerRecordMapper.insert(increMentServerRecord);
        increMentServerMapper.updateById(incrementServer);
    }

    public void closeServer(IncreMentServer increMentServer){
        increMentServerMapper.updateById(increMentServer);
    }
}
