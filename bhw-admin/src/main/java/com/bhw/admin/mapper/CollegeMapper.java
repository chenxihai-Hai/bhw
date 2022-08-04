package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.SchoolBean;
import com.bhw.admin.vo.CollegesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollegeMapper extends BaseMapper<SchoolBean> {
    int selectCollegeCountByType(@Param("type") Integer type);

    IPage<CollegesVo> selectCollegeByType(Page page,@Param("type") Integer type);

    IPage<SchoolBean> selectCollegeList(Page page,@Param("sh") SchoolBean sh);
}
