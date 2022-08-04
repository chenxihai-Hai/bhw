package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.AddressManageMapper;
import com.bhw.user.pojo.AddressManage;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/addressManage")
public class AddressManageRest {

    @Resource
    AddressManageMapper addressManageMapper;

    @RequestMapping("/qryAddressManageList")
    @ResponseBody
    public Response<Object> qryAddressManageList(AddressManage addressManage, Page page){
        Response<Object> response = new Response<>();
        IPage<AddressManage> qryAddressManageList = addressManageMapper.qryAddressManageList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<AddressManage>(page.getCurPage(), page.getPageSize()), addressManage);
        List<AddressManage> records = qryAddressManageList.getRecords();
        page.setCounts((int)qryAddressManageList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryAddressManageList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveAddressManage")
    @ResponseBody
    public Response<Object> saveAddressManage(AddressManage addressManage){
        Response<Object> response = new Response<>();
        addressManageMapper.insert(addressManage);
        return response;
    }

    @RequestMapping("/upAddressManage")
    @ResponseBody
    public Response<Object> upAddressManage(AddressManage addressManage){
        Response<Object> response = new Response<>();
        addressManageMapper.updateById(addressManage);
        return response;
    }

    @RequestMapping("/delAddressManage")
    @ResponseBody
    public Response<Object> delAddressManage(AddressManage addressManage){
        Response<Object> response = new Response<>();
        addressManageMapper.deleteById(addressManage.getId());
        return response;
    }

}
