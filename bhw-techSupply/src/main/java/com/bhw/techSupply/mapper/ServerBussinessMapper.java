package com.bhw.techSupply.mapper;

import com.bhw.techSupply.vo.ServerBussinessVo;

import java.util.List;

public interface ServerBussinessMapper {
    List<ServerBussinessVo> selectByType(Integer type);
}
