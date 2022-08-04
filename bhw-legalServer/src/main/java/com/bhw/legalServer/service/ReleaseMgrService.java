package com.bhw.legalServer.service;



import com.bhw.legalServer.dto.ReleaseReq;
import com.bhw.legalServer.mapper.LegalServerInfoMapper;
import com.bhw.legalServer.mapper.ServerBussinessMapper;
import com.bhw.legalServer.pojo.LegalServerInfo;
import com.bhw.legalServer.vo.ServerBussinessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReleaseMgrService {

    @Resource
    private LegalServerInfoMapper legalServerInfoMapper;

    @Resource
    private  ServerBussinessMapper bussinessMapper;

    public List<ServerBussinessVo> getBussinessType(Integer type){
        List<ServerBussinessVo> list=bussinessMapper.selectByType(type);
        return list;
    }

    public boolean saveReleaseInfo(ReleaseReq req){
        Date createTime=new Date();
        LegalServerInfo info=new LegalServerInfo();
        info.setCheckType(req.getCheckType());
        info.setBusinessType(req.getBusinessType());
        info.setOnPriceType(req.getOnPriceType());
        info.setOriginalPrice(req.getOriginalPrice());
        info.setPrice(req.getPrice());
        info.setBudget(req.getBudget());
        info.setAddress(req.getAddress());
        info.setCityId(req.getCityId());
        info.setFreightType(req.getFreightType());
        info.setContactName(req.getContactName());
        info.setContactUnit(req.getContactUnit());
        info.setContactPhone(req.getContactPhone());
        info.setKeyWord(req.getKeyWord());
        info.setServerName(req.getServerName());
        info.setServerRemark(req.getServerRemark());
        info.setIntellectualType(1);
        info.setResult(0);//待解决
        info.setStatus(req.getStatus());
        info.setCreateTime(createTime);
        info.setSource(req.getSource());
        info.setUserId(req.getUserId());
        if(legalServerInfoMapper.insertLs(info)){
            return true;
        }
        return false;
    }
}
