package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.BrokerRegister;
import com.bhw.admin.pojo.CityBean;
import com.bhw.admin.pojo.ProvinceBean;
import com.bhw.admin.pojo.ServerAdvisory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServerAdvisoryMapper extends BaseMapper<ServerAdvisory> {

    public IPage<ServerAdvisory> selectServerAdvisoryList(Page page, @Param("serverAdv")ServerAdvisory serverAdv);
}
