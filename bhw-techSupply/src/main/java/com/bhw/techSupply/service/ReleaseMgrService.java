package com.bhw.techSupply.service;



import com.bhw.techSupply.dto.ReleaseReq;
import com.bhw.techSupply.mapper.ServerBussinessMapper;
import com.bhw.techSupply.mapper.TechSupplyInfoMapper;
import com.bhw.techSupply.pojo.TechSupplyInfo;
import com.bhw.techSupply.vo.ServerBussinessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReleaseMgrService {

    @Autowired
    private TechSupplyInfoMapper techSupplyInfoMapper;

    @Autowired
    private  ServerBussinessMapper bussinessMapper;

    public List<ServerBussinessVo> getBussinessType(Integer type){
        List<ServerBussinessVo> list=bussinessMapper.selectByType(type);
        return list;
    }

    public boolean saveReleaseInfo(ReleaseReq req, String downloadPath){
        Date createTime=new Date();
        TechSupplyInfo info=new TechSupplyInfo();
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

        info.setKeyWord(req.getKeyWord());
        info.setServerName(req.getServerName());
        info.setServerRemark(req.getServerRemark());
        info.setIntellectualType(1);
        info.setResult(0);//待解决
        info.setStatus(req.getStatus());
        info.setCreateTime(createTime);
        info.setDataFilePath(downloadPath);
       /* if(techSupplyInfoMapper.insert(info)){
            return true;
        }*/
        return false;
    }
}
