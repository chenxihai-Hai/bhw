package com.bhw.admin.service;

import com.bhw.admin.mapper.CityMapper;
import com.bhw.admin.pojo.CityBean;
import com.bhw.admin.pojo.ProvinceBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CityService {

    @Resource
    CityMapper cityMapper;

    public List<ProvinceBean> qryProvinceList(){
        return cityMapper.selectProvinceList();
    };

    public List<CityBean> qryCityList(CityBean city){
        return cityMapper.selectCityList(city);
    };

}
