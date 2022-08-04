package com.bhw.fiscalTax.mapper;



import com.bhw.fiscalTax.vo.ServerBussinessVo;

import java.util.List;

public interface ServerBussinessMapper {
    List<ServerBussinessVo> selectByType(Integer type);
}
