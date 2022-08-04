package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bhw.admin.pojo.BackRoleMenu;
import com.bhw.admin.pojo.BackUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BackRoleMenuMapper extends BaseMapper<BackRoleMenu> {
    public List<BackRoleMenu> getRoleMenuByRole(@Param("roleId") Integer roleId);
}
