package com.bhw.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.mapper.UserOrderMapper;
import com.bhw.user.vo.UserOrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserOrderService {


    @Resource
    private UserOrderMapper userOrderMapper;

    public List<UserOrderVo> qryUserOrderList(UserOrderVo userOrderVo, com.bhw.common.base.Page page){
        IPage<UserOrderVo> userMbBeanIPage = userOrderMapper.qryUserOrderList(new Page<UserOrderVo>(page.getCurPage(), page.getPageSize()),userOrderVo);
        List<UserOrderVo> list = userMbBeanIPage.getRecords();
        page.setCounts((int)userMbBeanIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)userMbBeanIPage.getCurrent());
        return list;
    }

    public void upUserOrderStatus(UserOrderVo userOrderVo){
        userOrderMapper.upUserOrderStatus(userOrderVo);
    }

}
