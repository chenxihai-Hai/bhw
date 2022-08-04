package com.bhw.bhwtechology.servicce;

import com.bhw.bhwtechology.dto.ReleaseReq;
import com.bhw.bhwtechology.mapper.ServerBussinessMapper;
import com.bhw.bhwtechology.mapper.TechologyInfoMapper;
import com.bhw.bhwtechology.pojo.TechologyInfo;
import com.bhw.bhwtechology.vo.ServerBussinessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReleaseMgrService {
    @Autowired
    private  ServerBussinessMapper serverBussinessMapper;
    @Autowired
    private TechologyInfoMapper techologyInfoMapper;

    public List<ServerBussinessVo>  getBussinessType(Integer type){
        List<ServerBussinessVo> list=serverBussinessMapper.selectByType(type);
        return list;
    }
    public boolean saveReleaseInfo(ReleaseReq req){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date cutoffDate=formatter.parse(req.getCutoffDate(),pos);
        Date createTime=new Date();
        TechologyInfo info=new TechologyInfo();
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
        info.setTechName(req.getTechName());
        info.setTechCode(req.getTechCode());
//        info.setCutoffDate(cutoffDate);
//        boolean flag=techologyInfoMapper.insert(info);
        return false;
    }

}
