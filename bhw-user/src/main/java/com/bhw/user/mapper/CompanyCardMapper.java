package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.user.pojo.BhwCompanyCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyCardMapper extends BaseMapper<BhwCompanyCard> {
    public boolean  insertSelective(BhwCompanyCard record);
    public boolean deleteByUserId(@Param("idList") List<Integer> idList);
}
