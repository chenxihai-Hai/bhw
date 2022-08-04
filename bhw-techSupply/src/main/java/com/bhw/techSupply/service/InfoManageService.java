package com.bhw.techSupply.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.techSupply.dto.InfoAuditReq;
import com.bhw.techSupply.dto.InfoListReq;
import com.bhw.techSupply.mapper.TechSupplyInfoMapper;
import com.bhw.techSupply.vo.InfoListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InfoManageService {
    @Autowired
    private TechSupplyInfoMapper techSupplyInfoMapper;

    public List<InfoListVo> getInfoList(InfoListReq req, Page page){
        IPage<InfoListVo> iPage=techSupplyInfoMapper.selectInfoList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<InfoListVo>(page.getCurPage(),page.getPageSize()),req);
        List<InfoListVo> infoListVoList=iPage.getRecords();
        List<InfoListVo> model=new ArrayList<>();
        for(InfoListVo item : infoListVoList){
            if(item.getCheckType()==1){
                item.setCheckTypeName("供");
            }else if(item.getCheckType()==2){
                item.setCheckTypeName("需");
            }
            if(null != item.getOnPriceType() && item.getOnPriceType()==2){
                item.setOriginalPrice("面议");
                item.setPrice("面议");
            }
            item.setAddress(item.getProvinceName()+item.getCityName());
            model.add(item);
        }
        page.setCounts((int)iPage.getTotal(),page.getPageSize());
        page.setCurPage((int)iPage.getCurrent());
        return model;
    }

    public boolean infoAudit(InfoAuditReq req){
        boolean flag=false;
        List<Integer> serverId=new ArrayList<>();
        String idlist=req.getIdList();
        if(idlist.contains(",")){
            String[] idListArray=req.getIdList().split(",");
            for(String item:idListArray){
                serverId.add(Integer.parseInt(item));
            }
        }else{
            serverId.add(Integer.parseInt(idlist));
        }
        flag=techSupplyInfoMapper.updateAuditStstus(req.getType(),serverId);
        return flag;
    }
}
