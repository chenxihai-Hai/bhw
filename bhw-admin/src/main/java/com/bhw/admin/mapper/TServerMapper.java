package com.bhw.admin.mapper;

import com.bhw.admin.pojo.TServerBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TServerMapper {
    public List<TServerBean> selectall();
}
