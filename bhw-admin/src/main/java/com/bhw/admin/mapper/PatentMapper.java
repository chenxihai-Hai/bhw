package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.admin.pojo.CityBean;
import com.bhw.admin.pojo.PatentBean;
import com.bhw.admin.pojo.ProvinceBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatentMapper extends BaseMapper<PatentBean> {

}
