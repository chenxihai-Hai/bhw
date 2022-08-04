package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.util.BankUtil;
import com.bhw.common.util.ThirdpartyJhUtil;
import com.bhw.user.mapper.PayMentAccoutMapper;
import com.bhw.user.mapper.PayMentBankCardMapper;
import com.bhw.user.pojo.PayMentAccout;
import com.bhw.user.pojo.PayMentBankCard;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/payBankCard")
public class BankCardRest {
    private final static Logger logger = LoggerFactory.getLogger(BankCardRest.class);

    @Resource
    private PayMentBankCardMapper payMentBankCardMapper;

    @Resource
    private PayMentAccoutMapper payMentAccoutMapper;

    @RequestMapping("/bankCardVerify")
    @ResponseBody
    public Response<Object> bankCardVerify(PayMentBankCard payMentBankCard){
        Response<Object> response = new Response<>();
        int i = ThirdpartyJhUtil.bankCardVerify(payMentBankCard.getRealName(), payMentBankCard.getIdCard(), payMentBankCard.getBankCard(), payMentBankCard.getMobile());
        if(i!=1){
            response.setRetCode(-1);
            response.setMessage("持卡人信息与银行不匹配!");
            return response;
        }
        return response;
    }

    @RequestMapping("/saveBankCardInfo")
    @ResponseBody
    public Response<Object> saveBankCardInfo(PayMentBankCard payMentBankCard,String password){
        Response<Object> response = new Response<>();
        int i = ThirdpartyJhUtil.bankCardVerify(payMentBankCard.getRealName(), payMentBankCard.getIdCard(), payMentBankCard.getBankCard(), payMentBankCard.getMobile());
        if(i!=1){
            response.setRetCode(-1);
            response.setMessage("持卡人信息与银行不匹配!");
            return response;
        }
        if(StringUtils.isNotEmpty(password)){
            PayMentAccout payMentAccout = payMentAccoutMapper.selectById(payMentBankCard.getPayAccountId());
            if(null != payMentAccout && !password.equals(payMentAccout.getPayPassWord())){
                response.setRetCode(-1);
                response.setMessage("支付密码不正确!");
                return response;
            }
        }

        String bankNameCode= BankUtil.getCardDetail(payMentBankCard.getBankCard());
        if(StringUtils.isNotEmpty(bankNameCode)) {
            payMentBankCard.setBankName(bankNameCode);
        }
        payMentBankCardMapper.insert(payMentBankCard);
        return response;
    }


    @RequestMapping("/qryBankCardList")
    @ResponseBody
    public Response<Object> qryBankCardList(PayMentBankCard payMentBankCard, Page page){
        Response<Object> response = new Response<>();
        IPage<PayMentBankCard> selectPage = payMentBankCardMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<PayMentBankCard>(page.getCurPage(), page.getPageSize()), new QueryWrapper<PayMentBankCard>(payMentBankCard));
        List<PayMentBankCard> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/delBankCard")
    @ResponseBody
    public Response<Object> delBankCard(PayMentBankCard payMentBankCard){
        Response<Object> response = new Response<>();
        payMentBankCardMapper.deleteById(payMentBankCard.getId());
        return response;
    }
}
