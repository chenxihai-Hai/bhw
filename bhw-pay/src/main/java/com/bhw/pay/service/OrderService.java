package com.bhw.pay.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bhw.pay.mapper.OrderMapper;
import com.bhw.pay.mapper.OrderProductMapper;
import com.bhw.pay.mapper.OrderUserMapper;
import com.bhw.pay.pojo.OrderBean;
import com.bhw.pay.pojo.OrderProduct;
import com.bhw.pay.pojo.OrderUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class OrderService extends ServiceImpl<OrderMapper,OrderBean> {

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderUserMapper orderUserMapper;

    @Resource
    OrderProductMapper orderProductMapper;

    public void addOrder(OrderBean orderBean){
        orderMapper.insert(orderBean);
        OrderUser orderUser = new OrderUser();
        orderUser.setOrderId(orderBean.getOrderId());
        orderUser.setUserId(orderBean.getUserId());
        orderUserMapper.insert(orderUser);
        String[] productId = orderBean.getProductId().split(",");
        for (String pId:productId) {
            if(StringUtils.isEmpty(pId)){
                continue;
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(orderBean.getOrderId());
            orderProduct.setProductId(Integer.valueOf(pId));
            orderProductMapper.insert(orderProduct);
        }
    }

    public void upOrder(OrderBean orderBean){
        orderMapper.updateById(orderBean);
    }

    public List<OrderBean> qryOrderList(OrderBean orderBean, com.bhw.common.base.Page page){
        Wrapper<OrderBean> queryWrapper =  new QueryWrapper<OrderBean>(orderBean);
        Page<OrderBean> serverProjectBeanPage = new Page<OrderBean>(page.getCurPage(), page.getPageSize());
        IPage<OrderBean> orderBeanIPage = orderMapper.selectPage(serverProjectBeanPage, queryWrapper);
       /* IPage<OrderBean> refundRecordIPage = orderMapper.qryOrderList(new Page<OrderBean>(page.getCurPage(), page.getPageSize()),orderBean);
        List<OrderBean> list = refundRecordIPage.getRecords();
        page.setCounts((int)refundRecordIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)refundRecordIPage.getCurrent());*/
        List<OrderBean> list = orderBeanIPage.getRecords();
        page.setCounts((int)orderBeanIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)orderBeanIPage.getCurrent());
        return list;
    }

}
