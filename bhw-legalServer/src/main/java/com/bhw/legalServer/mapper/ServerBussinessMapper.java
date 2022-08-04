package com.bhw.legalServer.mapper;

import com.bhw.legalServer.vo.ServerBussinessVo;

import java.util.List;

public interface ServerBussinessMapper {
    List<ServerBussinessVo> selectByType(Integer type);
}
