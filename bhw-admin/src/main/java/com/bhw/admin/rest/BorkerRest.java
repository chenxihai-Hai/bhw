package com.bhw.admin.rest;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.BrokerEvaluateMapper;
import com.bhw.admin.mapper.BrokerRegisterMapper;
import com.bhw.admin.pojo.BrokerEvaluate;
import com.bhw.admin.pojo.BrokerRegister;
import com.bhw.admin.service.BorkerService;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 经纪人注册
 */
@RestController
@RefreshScope
@RequestMapping("/broker")
public class BorkerRest {

    private Logger log= LoggerFactory.getLogger(BorkerRest.class);
    @Autowired
    private BorkerService borkerService;

    @Resource
    BrokerRegisterMapper brokerRegisterMapper;

    @Resource
    BrokerEvaluateMapper brokerEvaluateMapper;

    @RequestMapping("/qryBrokerList")
    @ResponseBody
    public Response<Object> qryBrokerList(BrokerRegister brokerRegister, Page page){
        Response<Object> response = new Response<>();
        IPage<BrokerRegister> qryBrokerList = brokerRegisterMapper.qryBrokerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BrokerRegister>(page.getCurPage(), page.getPageSize()), brokerRegister);
        List<BrokerRegister> records = qryBrokerList.getRecords();
        page.setCounts((int)qryBrokerList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryBrokerList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/backQryBrokerList")
    @ResponseBody
    public Response<Object> backQryBrokerList(BrokerRegister brokerRegister, Page page){
        Response<Object> response = new Response<>();
        IPage<BrokerRegister> qryBrokerList = brokerRegisterMapper.backQryBrokerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BrokerRegister>(page.getCurPage(), page.getPageSize()), brokerRegister);
        List<BrokerRegister> records = qryBrokerList.getRecords();
        page.setCounts((int)qryBrokerList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryBrokerList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/backQryBrokerTurncverList")
    @ResponseBody
    public Response<Object> backQryBrokerTurncverList(BrokerRegister brokerRegister, Page page){
        Response<Object> response = new Response<>();
        Wrapper<BrokerRegister> qw =  new QueryWrapper<>(brokerRegister);
        ((QueryWrapper<BrokerRegister>) qw).groupBy("name");
        ((QueryWrapper<BrokerRegister>) qw).orderByDesc("createTime");
        IPage<BrokerRegister> brokerRegisterIPage = brokerRegisterMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BrokerRegister>(page.getCurPage(), page.getPageSize()),qw );
        List<BrokerRegister> records1 = brokerRegisterIPage.getRecords();
        Page p = new Page();
        p.setPageSize(1000);
        p.setCurPage(1);
        records1.forEach(bk->{
            BrokerRegister br = new BrokerRegister();
            if (StringUtils.isNotEmpty(bk.getPhone())) {
                br.setPhone(bk.getPhone());
            }else{
                br.setUserName(bk.getName());
            }
            IPage<BrokerRegister> qryBrokerList = brokerRegisterMapper.backQryBrokerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BrokerRegister>(p.getCurPage(), p.getPageSize()), br);
            List<BrokerRegister> records = qryBrokerList.getRecords();
            if(records.size()>0){
                bk.setSeniority(records.get(0).getBrokerName());
            }else{
                bk.setSeniority("");
            }
            bk.setTypeList(records);
        });
//        IPage<BrokerRegister> qryBrokerList = brokerRegisterMapper.backQryBrokerList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BrokerRegister>(page.getCurPage(), page.getPageSize()), brokerRegister);
//        List<BrokerRegister> records = qryBrokerList.getRecords();
        page.setCounts((int)brokerRegisterIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)brokerRegisterIPage.getCurrent());
        response.setData(records1);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveBrokerEvaluate")
    @ResponseBody
    public Response<Object> saveBrokerEvaluate(BrokerEvaluate brokerEvaluate){
        Response<Object> response = new Response<>();
        brokerEvaluateMapper.insert(brokerEvaluate);
        return response;
    }

    @RequestMapping(value = "/brokerRegister",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> saveBrokerAdvisory(BrokerRegister req){
        Response<Object> response = new Response<>();
        brokerRegisterMapper.insert(req);
        return response;
    }

    @RequestMapping(value = "/upBrokerAdvisory",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> upBrokerAdvisory(BrokerRegister req){
        Response<Object> response = new Response<>();
        brokerRegisterMapper.updateById(req);
        return response;
    }

    @RequestMapping(value = "/delBrokerRegister",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> delBrokerRegister(String idList){
        Response<Object> response = new Response<>();
        brokerRegisterMapper.deleteBatchIds(Arrays.asList(idList.split(",")));
        return response;
    }
}
