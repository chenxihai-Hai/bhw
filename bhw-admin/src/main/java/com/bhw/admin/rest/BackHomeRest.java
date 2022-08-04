package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.BackOperateRecordMapper;
import com.bhw.admin.pojo.AdvertBean;
import com.bhw.admin.pojo.BackOperateRecord;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/backHome")
public class BackHomeRest {

    @Resource
    BackOperateRecordMapper backOperateRecordMapper;

    @RequestMapping("/saveBackOperate")
    @ResponseBody
    public Response<Object> saveBackOperate(BackOperateRecord backOperateRecord){
        Response<Object> response = new Response<>();
        backOperateRecordMapper.insert(backOperateRecord);
        return response;
    }

    @RequestMapping("/qryBackOperate")
    @ResponseBody
    public Response<Object> qryBackOperate(BackOperateRecord backOperateRecord, Page page){
        Response<Object> response = new Response<>();
        Wrapper<BackOperateRecord> queryW =  new QueryWrapper<>(backOperateRecord);
        ((QueryWrapper<BackOperateRecord>) queryW).orderByDesc("createTime");
        IPage<BackOperateRecord> selectPage = backOperateRecordMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<BackOperateRecord>(page.getCurPage(), page.getPageSize()),queryW );
        List<BackOperateRecord> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }
}
