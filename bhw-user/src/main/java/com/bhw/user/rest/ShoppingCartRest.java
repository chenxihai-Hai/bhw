package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.user.mapper.ShoppingCartMapper;
import com.bhw.user.pojo.ShoppingCart;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/shoppingCart")
public class ShoppingCartRest {

    @Resource
    ShoppingCartMapper shoppingCartMapper;

    /**
     * 查询用户购物车
     * */
    @RequestMapping("/qryUserShoppingCartList")
    @ResponseBody
    public Response<Object> qryUserShoppingCartList(ShoppingCart shoppingCart, Page page){
        Response<Object> response = new Response<>();
        Wrapper query = new QueryWrapper(shoppingCart);
        ((QueryWrapper) query).orderByDesc("createTime");
        IPage iPage = shoppingCartMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ShoppingCart>(page.getCurPage(), page.getPageSize()), query);
        List<ShoppingCart> records = iPage.getRecords();
        page.setCounts((int)iPage.getTotal(),page.getPageSize());
        page.setCurPage((int)iPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveUserShopping")
    @ResponseBody
    public Response<Object> saveUserShopping(ShoppingCart shoppingCart){
        Response<Object> response = new Response<>();
        shoppingCartMapper.insert(shoppingCart);
        return response;
    }

    @RequestMapping("/delUserShopping")
    @ResponseBody
    public Response<Object> delUserShopping(String ids){
        Response<Object> response = new Response<>();
        shoppingCartMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
        return response;
    }
}
