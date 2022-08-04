package com.bhw.admin.service;

import com.bhw.admin.dto.BrokerRegisterReq;
import com.bhw.admin.mapper.BrokerRegisterMapper;
import com.bhw.admin.pojo.BrokerRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BorkerService {
    @Autowired
    private BrokerRegisterMapper brokerRegisterMapper;

   public  boolean brokerRegister(BrokerRegisterReq req,String downloadPath){
       BrokerRegister insert=new BrokerRegister();
       insert.setProfessionId(req.getProfessionId());
       insert.setEmail(req.getEmail());
       insert.setMainAchieve(req.getMainAchieve());
       insert.setName(req.getName());
       insert.setPhone(req.getPhone());
       insert.setSeniority(req.getSeniority());
       insert.setSex(req.getSex());
       insert.setStatus(req.getStatus());
       insert.setPhoto(downloadPath);
       int count=brokerRegisterMapper.insert(insert);
       if(count>0){
           return true;
       }
       return false;
    }
}
