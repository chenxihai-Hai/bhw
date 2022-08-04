package com.bhw.serverManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.serverManage.pojo.ServerProjectBean;
import com.bhw.serverManage.vo.AiListVo;
import com.bhw.serverManage.vo.RequireVo;
import com.bhw.serverManage.vo.TechAchievementVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServiceDwMapper extends BaseMapper<ServerProjectBean> {

    public IPage<AiListVo> selectServiceDwList(Page<AiListVo> page, @Param("ai") AiListVo ai);

    public IPage<TechAchievementVo> qryTechAchievemnetList(Page<TechAchievementVo> page, @Param("tech") TechAchievementVo tech);

}
