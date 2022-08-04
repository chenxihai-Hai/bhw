package com.bhw.serverManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.serverManage.vo.OrderVo;
import com.bhw.serverManage.pojo.ServerProjectBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IntellectualMapper  extends BaseMapper<ServerProjectBean> {

    public IPage<OrderVo> qryIntellectuaOrderList(Page<OrderVo> page, @Param("projectVo")OrderVo orderVo);

    public IPage<OrderVo> backQryOrderList(Page<OrderVo> page, @Param("projectVo")OrderVo orderVo);

    public IPage<OrderVo> qryIntellectuaProject(Page<OrderVo> page, @Param("project")ServerProjectBean project);
}
