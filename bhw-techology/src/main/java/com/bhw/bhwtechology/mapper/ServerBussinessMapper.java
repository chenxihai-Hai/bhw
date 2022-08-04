package com.bhw.bhwtechology.mapper;



import com.bhw.bhwtechology.vo.ServerBussinessVo;

import java.util.List;

public interface ServerBussinessMapper {
    List<ServerBussinessVo> selectByType(Integer type);
}
