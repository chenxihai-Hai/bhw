package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.UserMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMemberMapper extends BaseMapper<UserMember> {

    IPage<UserMember> qryUserMemberList(Page page, @Param("userMember") UserMember userMember);

}
