package com.bhw.techSupply.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.techSupply.dto.InfoListReq;
import com.bhw.techSupply.dto.ServerListPageReq;
import com.bhw.techSupply.pojo.TechSupplyInfo;
import com.bhw.techSupply.vo.InfoListVo;
import com.bhw.techSupply.vo.ServerListPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TechSupplyInfoMapper extends BaseMapper<TechSupplyInfo> {
//    boolean insert(TechSupplyInfo record);

    IPage<ServerListPageVo> selectServerList(Page page, @Param("req") ServerListPageReq req);

    int updateByIdList(List<Integer> idList);

    IPage<InfoListVo> selectInfoList(Page page, @Param("req") InfoListReq req);

    boolean updateAuditStstus(@Param("auditStatus") Integer type,@Param("idList") List<Integer> serverId);
}
