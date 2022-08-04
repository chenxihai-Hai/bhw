package com.bhw.fiscalTax.service;

import com.bhw.fiscalTax.dto.releaseTaxReq;
import com.bhw.fiscalTax.mapper.FiscalTaxInfoMapper;
import com.bhw.fiscalTax.mapper.ServerBussinessMapper;
import com.bhw.fiscalTax.pojo.FiscalTaxInfo;
import com.bhw.fiscalTax.vo.ServerBussinessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReleaseMgrService {

    @Resource
    private FiscalTaxInfoMapper fiscalTaxInfoMapper;

    @Resource
    private ServerBussinessMapper bussinessMapper;

    public List<ServerBussinessVo> getBussinessType(Integer type){
        List<ServerBussinessVo> list=bussinessMapper.selectByType(type);
        return list;
    }

    public boolean saveReleaseInfo(releaseTaxReq req){
        Date createTime=new Date();
        FiscalTaxInfo info=new FiscalTaxInfo();
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
        info.setUserId(req.getUserId());
        info.setSource(req.getSource());
        if(fiscalTaxInfoMapper.saveInfo(info)){
            return true;
        }
        return false;
    }
}
