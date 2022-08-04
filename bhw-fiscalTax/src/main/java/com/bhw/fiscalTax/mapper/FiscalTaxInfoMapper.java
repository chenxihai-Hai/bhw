package com.bhw.fiscalTax.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.fiscalTax.dto.ServerListPageReq;
import com.bhw.fiscalTax.pojo.FiscalTaxInfo;
import com.bhw.fiscalTax.vo.ServerListPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FiscalTaxInfoMapper  extends BaseMapper<FiscalTaxInfo> {
    boolean saveInfo( FiscalTaxInfo record);

    IPage<ServerListPageVo> selectServerList(Page page,@Param("req") ServerListPageReq req);

    IPage<FiscalTaxInfo> backSelectServerList(Page page,@Param("req") ServerListPageReq req);

    int updateByIdList(List<Integer> idList);

    boolean updateAuditStstus(@Param("auditStatus") Integer type,@Param("idList") List<Integer> serverId);
}
