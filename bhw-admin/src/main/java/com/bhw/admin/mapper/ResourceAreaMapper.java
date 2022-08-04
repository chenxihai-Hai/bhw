package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.dto.ResourceAreaRangeReq;
import com.bhw.admin.pojo.ResourceAreaBean;
import com.bhw.admin.vo.AreaTopFive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceAreaMapper extends BaseMapper<ResourceAreaBean> {
    int selectCountByType(@Param("type") Integer type);

    List<AreaTopFive> selectProvinceRank(@Param("type") Integer type);

    IPage<ResourceAreaBean> selectResourceAreaRange(Page page,@Param("req") ResourceAreaRangeReq req);
    int insertList(List<ResourceAreaBean> list);
}
