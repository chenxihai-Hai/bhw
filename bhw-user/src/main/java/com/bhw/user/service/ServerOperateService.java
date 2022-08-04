package com.bhw.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.user.mapper.ServerHistoryMapper;
import com.bhw.user.pojo.AddressManage;
import com.bhw.user.pojo.ServerHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServerOperateService {
    @Autowired
    private ServerHistoryMapper serverHistoryMapper;

    public List<ServerHistory> qryList(Integer userid,Integer operateType){
        Map<String,Object> map=new HashMap<>();
        map.put("userid",userid);
        map.put("operateType",operateType);
        List<ServerHistory> list= serverHistoryMapper.selectByUserIdAndOperateType(map);
        return list;
    }

    public List<ServerHistory> qryStatisticsDate(ServerHistory serverHistory, Page page){
        IPage<ServerHistory> selectStatisticsByUserIdAndOperateType = serverHistoryMapper.selectStatisticsByUserIdAndOperateType(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerHistory>(page.getCurPage(), page.getPageSize()), serverHistory);
        List<ServerHistory> records = selectStatisticsByUserIdAndOperateType.getRecords();
        page.setCounts((int)selectStatisticsByUserIdAndOperateType.getTotal(),page.getPageSize());
        page.setCurPage((int)selectStatisticsByUserIdAndOperateType.getCurrent());
        return records;
    }

    public List<ServerHistory> qryServerHistoryList(ServerHistory serverHistory, Page page){
        IPage<ServerHistory> selectServerHistoryList = serverHistoryMapper.selectServerHistoryList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ServerHistory>(page.getCurPage(), page.getPageSize()), serverHistory);
        List<ServerHistory> records = selectServerHistoryList.getRecords();
        page.setCounts((int)selectServerHistoryList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectServerHistoryList.getCurrent());
        return records;
    }


    public boolean deleteRecord(List<Integer> idList){
        boolean flag=serverHistoryMapper.deleteRecord(idList);
        return flag;
    }

    public void saveRecord(ServerHistory serverHistory){
        serverHistoryMapper.insert(serverHistory);
    }
}
