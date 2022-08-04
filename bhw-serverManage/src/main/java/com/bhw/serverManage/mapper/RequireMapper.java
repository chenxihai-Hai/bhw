package com.bhw.serverManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.serverManage.vo.RequireVo;
import com.bhw.serverManage.pojo.ServerProjectBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RequireMapper extends BaseMapper<ServerProjectBean> {

    public IPage<RequireVo> selectRequireList(Page<RequireVo> page, @Param("require")RequireVo require);

    public IPage<RequireVo> selectScienceRequireList(Page<RequireVo> page, @Param("require")RequireVo require);

}
