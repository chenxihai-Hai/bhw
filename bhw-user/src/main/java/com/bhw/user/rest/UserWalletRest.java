package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.UserOrderMapper;
import com.bhw.user.mapper.UserWIthdrawMapper;
import com.bhw.user.mapper.UserWalletMapper;
import com.bhw.user.pojo.UserWallet;
import com.bhw.user.pojo.UserWithdraw;
import com.bhw.user.vo.UserOrderVo;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/userWallet")
public class UserWalletRest {

    @Resource
    UserWalletMapper walletMapper;

    @Resource
    UserOrderMapper userOrderMapper;

    @Resource
    UserWIthdrawMapper userWIthdrawMapper;

    /**
     * 查询用户钱包
     * */
    @RequestMapping("/qryUserWallet")
    @ResponseBody
    public Response<Object> qryUserWallet(UserWallet userWallet){
        Response<Object> response = new Response<>();
        UserWallet userWallet1 = walletMapper.selectOne(new QueryWrapper<UserWallet>(userWallet));
        if(null == userWallet1 || null == userWallet1.getId()){
            walletMapper.insert(userWallet);
            userWallet1 = walletMapper.selectOne(new QueryWrapper<UserWallet>(userWallet));
        }
        UserOrderVo userOrderVo = new UserOrderVo();
        userOrderVo.setUserId(userWallet.getUserId());
        userOrderVo.setStatus(2);
        userOrderVo.setPayStatus(2);
        UserOrderVo stUserWalletMy = userOrderMapper.getStUserWalletMy(userOrderVo);
        if(null != stUserWalletMy){
            userWallet1.setMoneyRemaining(stUserWalletMy.getTotalPrice());
            userWallet1.setOrderIdTemp(stUserWalletMy.getOrderId());
        }else{
            userWallet1.setMoneyRemaining("0");
        }
        userOrderVo.setPayStatus(1);
        stUserWalletMy = userOrderMapper.getStUserWalletMy(userOrderVo);
        if(null != stUserWalletMy){
            userWallet1.setMoneyFreeze(stUserWalletMy.getTotalPrice());
        }else{
            userWallet1.setMoneyFreeze("0");
        }
        walletMapper.updateById(userWallet1);
        response.setData(userWallet1);
        return response;
    }

    @RequestMapping("/saveUserWithdraw")
    @ResponseBody
    public Response<Object> saveUserWithdraw(UserWithdraw userWithdraw){
        Response<Object> response = new Response<>();
        userWIthdrawMapper.insert(userWithdraw);
        String orderIdTemp = userWithdraw.getOrderIdTemp();
        String[] split = orderIdTemp.split(",");
        for (String orderId:split) {
            UserOrderVo userOrderVo = new UserOrderVo();
            userOrderVo.setOrderId(orderId);
            userOrderVo.setPayStatus(3);
            userOrderMapper.upUserOrderStatus(userOrderVo);
        }
        return response;
    }

    @RequestMapping("/upUserWithdraw")
    @ResponseBody
    public Response<Object> upUserWithdraw(UserWithdraw userWithdraw){
        Response<Object> response = new Response<>();
        userWIthdrawMapper.updateById(userWithdraw);
        return response;
    }

    @RequestMapping("/qryUserWithdrawList")
    @ResponseBody
    public Response<Object> qryUserWithdrawList(UserWithdraw userWithdraw,Page page){
        Response<Object> response = new Response<>();
        IPage<UserWithdraw> qryPayDealList = userWIthdrawMapper.qryUserWithdrawList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserWithdraw>(page.getCurPage(), page.getPageSize()), userWithdraw);
        List<UserWithdraw> records = qryPayDealList.getRecords();
        page.setCounts((int)qryPayDealList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryPayDealList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/upUserWalletRemaining")
    @ResponseBody
    public Response<Object> upUserWallet(UserWallet userWallet,UserOrderVo userOrderVo){
        Response<Object> response = new Response<>();
        UserOrderVo userOrderVo1 = userOrderMapper.qryUserWalletOrderList(userOrderVo);
        if(null != userOrderVo1){
            String totalPrice = userOrderVo1.getTotalPrice();
            UserWallet userWallet1 = walletMapper.selectOne(new QueryWrapper<UserWallet>(userWallet));
            if(userOrderVo1.getDealType()==1){
                String tRemaining = userWallet1.getTRemaining();
                userWallet1.setTRemaining((Double.valueOf(totalPrice)*10+Double.valueOf(tRemaining))+"");
            }else{
                String moneyRemaining = userWallet1.getMoneyRemaining();
                userWallet1.setMoneyRemaining((Double.valueOf(totalPrice)+Double.valueOf(moneyRemaining))+"");
            }
            walletMapper.updateById(userWallet1);
        }
        return response;
    }

    @RequestMapping("/qryPayDealList")
    @ResponseBody
    public Response<Object> qryPayDealList(UserOrderVo userOrderVo,Page page){
        Response<Object> response = new Response<>();
        IPage<UserOrderVo> qryPayDealList = userOrderMapper.qryPayDealList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserOrderVo>(page.getCurPage(), page.getPageSize()), userOrderVo);
        List<UserOrderVo> records = qryPayDealList.getRecords();
        page.setCounts((int)qryPayDealList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryPayDealList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/qryUserWalletOrderList")
    @ResponseBody
    public Response<Object> qryUserWalletOrderList(UserOrderVo userOrderVo,Page page){
        Response<Object> response = new Response<>();
        IPage<UserOrderVo> qryPayDealList = userOrderMapper.qryUserWalletOrderList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserOrderVo>(page.getCurPage(), page.getPageSize()), userOrderVo);
        List<UserOrderVo> records = qryPayDealList.getRecords();
        page.setCounts((int)qryPayDealList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryPayDealList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/backQryWalletOrderList")
    @ResponseBody
    public Response<Object> backQryWalletOrderList(UserOrderVo userOrderVo,Page page){
        Response<Object> response = new Response<>();
        IPage<UserOrderVo> qryPayDealList = userOrderMapper.backQryWalletOrderList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserOrderVo>(page.getCurPage(), page.getPageSize()), userOrderVo);
        List<UserOrderVo> records = qryPayDealList.getRecords();
        page.setCounts((int)qryPayDealList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryPayDealList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/backQryPayDealList")
    @ResponseBody
    public Response<Object> backQryPayDealList(UserOrderVo userOrderVo,Page page){
        Response<Object> response = new Response<>();
        IPage<UserOrderVo> qryPayDealList = userOrderMapper.backQryPayDealList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserOrderVo>(page.getCurPage(), page.getPageSize()), userOrderVo);
        List<UserOrderVo> records = qryPayDealList.getRecords();
        page.setCounts((int)qryPayDealList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryPayDealList.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

}
