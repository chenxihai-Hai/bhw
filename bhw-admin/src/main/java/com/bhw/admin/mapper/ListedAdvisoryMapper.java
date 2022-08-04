package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.admin.pojo.ListedAdvisory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListedAdvisoryMapper extends BaseMapper<ListedAdvisory> {
    int insertOne(ListedAdvisory insert);
}
