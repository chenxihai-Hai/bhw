package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.user.pojo.IncreMentServer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IncreMentServerMapper extends BaseMapper<IncreMentServer> {

    public int insertMoreTwo(List<IncreMentServer> serverList);
}
