package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.dto.EditUserReq;
import com.bhw.user.dto.UserListReq;
import com.bhw.user.pojo.BhwUser;
import com.bhw.user.vo.AuditUserListVo;
import com.bhw.user.vo.UserListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BhwUserMapper extends BaseMapper<BhwUser> {

    IPage<BhwUser> qryUser(Page page);

    void updateUserTxAccount(@Param("user")BhwUser user);

    BhwUser checkPwd(@Param("username") String username,@Param("phone") String phone);

    BhwUser selectByPhone(@Param("phone")String phone);

    BhwUser selectByUserName(@Param("username")String username);

    boolean updateByPhone(BhwUser updateRecord);

    boolean  insertSelective(BhwUser record);

    int selectIdByPhone(String phone);

    String selectPwdByPhone(@Param("phone")String phone);

    IPage<UserListVo> selectUserList(Page page,@Param("req") UserListReq req);

    boolean deleteById(@Param("idList") List<Integer> idList);

    boolean updateUserList(@Param("req")EditUserReq req);

    IPage<AuditUserListVo> selectAuditUserList(Page page, @Param("type") String type);

    boolean updateAuditStstus(@Param("auditStatus") Integer type,@Param("idList") List<Integer> serverId);

    int selectCountByMember(@Param("type") Integer type);

    List<Map> selectWeekAdd(@Param("startTime") String startTime, @Param("endTime") String endTime,
                            @Param("member") Integer member,@Param("merchantFlag") Integer merchantFlag);
}
