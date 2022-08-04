package com.bhw.legalServer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.legalServer.dto.ServerListPageReq;
import com.bhw.legalServer.mapper.LegalServerInfoMapper;
import com.bhw.legalServer.vo.ServerListPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplyMgrService {

    @Autowired
    LegalServerInfoMapper legalServerInfoMapper;

    public List<ServerListPageVo> getServerList(ServerListPageReq req, Page page){
        IPage<ServerListPageVo> model=legalServerInfoMapper.selectServerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerListPageVo>(page.getCurPage(),page.getPageSize()),req);
        List<ServerListPageVo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public boolean serverTakeOff(List<Integer> idList){
        int count=legalServerInfoMapper.updateByIdList(idList);
        if(count>0){
            return true;
        }
        return false;
    }
}
