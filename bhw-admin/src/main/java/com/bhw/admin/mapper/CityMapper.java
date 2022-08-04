package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.admin.pojo.CityBean;
import com.bhw.admin.pojo.ListedNoticeInfo;
import com.bhw.admin.pojo.ProvinceBean;
import com.bhw.admin.pojo.UserMbBean;
import com.bhw.admin.vo.ListedNoticeBirefVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityMapper extends BaseMapper<CityBean> {

    public List<ProvinceBean> selectProvinceList();

    public List<CityBean> selectCityList(@Param("city")CityBean city);
}
