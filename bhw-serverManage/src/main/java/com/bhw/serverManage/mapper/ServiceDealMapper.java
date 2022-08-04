package com.bhw.serverManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.serverManage.pojo.ServerProjectBean;
import com.bhw.serverManage.vo.RequireVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServiceDealMapper extends BaseMapper<ServerProjectBean> {

    public IPage<RequireVo> selectServiceDealList(Page<RequireVo> page, @Param("require") RequireVo require);

}
