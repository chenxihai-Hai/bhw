package com.bhw.bhwtechology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.bhwtechology.dto.InfoListReq;
import com.bhw.bhwtechology.dto.ServerListPageReq;
import com.bhw.bhwtechology.pojo.TechologyInfo;
import com.bhw.bhwtechology.vo.InfoListVo;
import com.bhw.bhwtechology.vo.ServerListPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TechologyInfoMapper extends BaseMapper<TechologyInfo> {
//    boolean insert(TechologyInfo record);

    IPage<ServerListPageVo> selectServerList(Page page, @Param("req") ServerListPageReq req);

    int updateByIdList(List<Integer> idList);

    IPage<InfoListVo> selectInfoList(Page page, @Param("req") InfoListReq req);

    boolean updateAuditStstus(@Param("auditStatus") Integer type,@Param("idList") List<Integer> serverId);
}
