package com.bhw.bhwtechology.servicce;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.bhwtechology.dto.ServerListPageReq;
import com.bhw.bhwtechology.mapper.TechologyInfoMapper;
import com.bhw.bhwtechology.vo.ServerListPageVo;
import com.bhw.common.base.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplyMgrService {

    @Autowired
    TechologyInfoMapper techologyInfoMapper;

    public List<ServerListPageVo> getServerList(ServerListPageReq req, Page page){
        IPage<ServerListPageVo> model=techologyInfoMapper.selectServerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerListPageVo>(page.getCurPage(),page.getPageSize()),req);
        List<ServerListPageVo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public boolean serverTakeOff(List<Integer> idList){
        int count=techologyInfoMapper.updateByIdList(idList);
        if(count>0){
            return true;
        }
        return false;
    }
}
