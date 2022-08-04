package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.NewsBean;
import com.bhw.admin.vo.NewsContentvO;
import com.bhw.admin.vo.NewsListVo;

import com.bhw.admin.vo.NewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper extends BaseMapper<NewsBean> {
    public IPage<NewsVo> selectByType( Page page,@Param("type") String type);

    public IPage<NewsListVo> selectNewsListPage(Page page,@Param("type") String type);

    public NewsContentvO selectContentById(Integer id);

    void upNewsInfo(@Param("id") Integer id);
}
