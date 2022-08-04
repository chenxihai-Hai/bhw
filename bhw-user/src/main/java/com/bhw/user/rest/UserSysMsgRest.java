package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.UserSysMsgMapper;
import com.bhw.user.pojo.UserSysMsg;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/userSysMsg")
public class UserSysMsgRest {

    @Resource
    UserSysMsgMapper userSysMsgMapper;

    @RequestMapping("/saveMsg")
    @ResponseBody
    public Response<Object> saveMsg(UserSysMsg userSysMsg){
        Response<Object> response = new Response<>();
        userSysMsgMapper.insert(userSysMsg);
        return response;
    }

    @RequestMapping("/qryUserSysMsgCounts")
    @ResponseBody
    public Response<Object> qryMsgCounts(UserSysMsg userSysMsg){
        Response<Object> response = new Response<>();
        Integer integer = userSysMsgMapper.selectCount(new QueryWrapper(userSysMsg));
        response.setData(integer);
        return response;
    }

    @RequestMapping("/qryUserSysMsgList")
    @ResponseBody
    public Response<Object> qryUserSysMsgList(UserSysMsg userSysMsg, Page page){
        Response<Object> response = new Response<>();
        QueryWrapper queryWrapper = new QueryWrapper(userSysMsg);
        queryWrapper.orderByDesc("createTime");
        IPage<UserSysMsg> selectPage = userSysMsgMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserSysMsg>(page.getCurPage(), page.getPageSize()),queryWrapper );
        List<UserSysMsg> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        UserSysMsg usm = new UserSysMsg();
        usm.setStatus(1);
        QueryWrapper upWrapper = new QueryWrapper(userSysMsg);
        List<Integer> ids = new ArrayList<>();
        records.forEach(userSysMsg1 -> {
            ids.add(userSysMsg1.getId());
        });
        upWrapper.in("id",ids);
        userSysMsgMapper.update(usm,upWrapper);
        return response;
    }
}
