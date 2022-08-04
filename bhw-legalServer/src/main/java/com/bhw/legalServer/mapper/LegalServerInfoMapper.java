package com.bhw.legalServer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.legalServer.dto.InfoListReq;
import com.bhw.legalServer.dto.ServerListPageReq;
import com.bhw.legalServer.pojo.LegalServerInfo;
import com.bhw.legalServer.vo.InfoListVo;
import com.bhw.legalServer.vo.ServerListPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LegalServerInfoMapper  extends BaseMapper<LegalServerInfo> {
    boolean insertLs(LegalServerInfo record);

    IPage<ServerListPageVo> selectServerList(Page page, @Param("req") ServerListPageReq req);

    int updateByIdList(List<Integer> idList);


    IPage<InfoListVo> selectInfoList(Page page, @Param("req") InfoListReq req);

    boolean updateAuditStstus(@Param("auditStatus") Integer type,@Param("idList") List<Integer> serverId);
}
