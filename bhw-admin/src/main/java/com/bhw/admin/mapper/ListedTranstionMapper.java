package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.admin.dto.ListedResultReq;
import com.bhw.admin.pojo.ListedTranstion;
import com.bhw.admin.vo.ListedMessageVo;
import com.bhw.admin.vo.ListedResultVo;
import com.bhw.admin.vo.ListedTranstionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListedTranstionMapper extends BaseMapper<ListedTranstion> {
    public int selectListedOnNumber(String status);

    public IPage<ListedMessageVo> selectListedMessageVo(Page page);

    public IPage<ListedResultVo> selectListedResult( Page page,@Param("req") ListedResultReq req);

    public IPage<ListedTranstionVo> backQryListedList(Page page, @Param("req") ListedTranstionVo req);

}
