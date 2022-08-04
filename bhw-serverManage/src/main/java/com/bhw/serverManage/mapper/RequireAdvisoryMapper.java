package com.bhw.serverManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.serverManage.pojo.RequireAdvisory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RequireAdvisoryMapper extends BaseMapper<RequireAdvisory> {

    public IPage<RequireAdvisory> selectRequireAdvisoryList(Page page, @Param("requireAdvisory") RequireAdvisory requireAdvisory);
}
