package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.pojo.ListingExpert;
import com.bhw.admin.pojo.UserMbBean;
import com.bhw.admin.vo.NewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserMbBean> {

    IPage<UserMbBean> qryUser(Page page);

    IPage<UserMbBean> selectByTypeAndBrokerid(Page page, @Param("brokerid") String brokerid);

    IPage<ListingExpert> selectListingExpert(Page page);
}
