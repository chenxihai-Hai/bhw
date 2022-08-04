package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.BhwUser;
import com.bhw.user.pojo.PhoneRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhoneRecordMapper extends BaseMapper<PhoneRecord> {

}
