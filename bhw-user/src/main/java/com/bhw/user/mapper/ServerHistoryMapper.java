package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.AddressManage;
import com.bhw.user.pojo.ServerHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ServerHistoryMapper extends BaseMapper<ServerHistory> {
    List<ServerHistory> selectByUserIdAndOperateType(@Param("map") Map params);

    boolean deleteRecord(List<Integer> idList);

    IPage<ServerHistory> selectStatisticsByUserIdAndOperateType(Page page, @Param("his") ServerHistory his);

    IPage<ServerHistory> selectServerHistoryList(Page page, @Param("his") ServerHistory his);
}
