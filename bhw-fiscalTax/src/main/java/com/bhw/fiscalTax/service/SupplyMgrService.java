package com.bhw.fiscalTax.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.fiscalTax.dto.ServerListPageReq;
import com.bhw.fiscalTax.mapper.FiscalTaxInfoMapper;
import com.bhw.fiscalTax.pojo.FiscalTaxInfo;
import com.bhw.fiscalTax.vo.ServerListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SupplyMgrService {

    @Autowired
    FiscalTaxInfoMapper fiscalTaxInfoMapper;

    public List<ServerListPageVo> getServerList(ServerListPageReq req, Page page){
        IPage<ServerListPageVo> model=fiscalTaxInfoMapper.selectServerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerListPageVo>(page.getCurPage(),page.getPageSize()),req);
        List<ServerListPageVo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public List<FiscalTaxInfo> backSelectServerList(ServerListPageReq req, Page page){
        IPage<FiscalTaxInfo> model=fiscalTaxInfoMapper.backSelectServerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerListPageVo>(page.getCurPage(),page.getPageSize()),req);
        List<FiscalTaxInfo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public boolean serverTakeOff(List<Integer> idList){
        int count=fiscalTaxInfoMapper.updateByIdList(idList);
        if(count>0){
            return true;
        }
        return false;
    }

    public boolean infoAudit(Integer status,List<Integer> list){
        return fiscalTaxInfoMapper.updateAuditStstus(status,list);
    }
}
