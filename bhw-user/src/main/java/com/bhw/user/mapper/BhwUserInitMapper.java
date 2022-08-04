package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.user.pojo.BhwUserInit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BhwUserInitMapper extends BaseMapper<BhwUserInit> {

    public boolean upUserInitPw(BhwUserInit userInit);

    public void insertUserInit(BhwUserInit userInit);

    public boolean updateByUserId(@Param("newPwd") String newPwd,@Param("userId") Integer userId);
}
