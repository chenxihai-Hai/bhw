package com.bhw.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.dto.ListedAdvisoryReq;
import com.bhw.admin.dto.ListedResultReq;
import com.bhw.admin.mapper.ListedAdvisoryMapper;
import com.bhw.admin.mapper.ListedNoticeInfoMapper;
import com.bhw.admin.mapper.ListedTranstionMapper;
import com.bhw.admin.pojo.ListedAdvisory;
import com.bhw.admin.pojo.ListedNoticeInfo;
import com.bhw.admin.pojo.ListedTranstion;
import com.bhw.admin.rest.ListedTransactionController;
import com.bhw.admin.util.RedisUtil;
import com.bhw.admin.vo.ListedMessageVo;
import com.bhw.admin.vo.ListedNoticeBirefVo;
import com.bhw.admin.vo.ListedResultVo;
import com.bhw.admin.vo.ListedTranstionVo;
import com.bhw.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ListedTranstionService {
    @Autowired
    private ListedTranstionMapper listedTranstionMapper;

    @Autowired
    private ListedNoticeInfoMapper listedNoticeInfoMapper;

    @Autowired
    private ListedAdvisoryMapper listedAdvisoryMapper;

    @Resource
    private RedisUtil redisUtil;

    public int listedOnNumber(String status){
        int count=listedTranstionMapper.selectListedOnNumber(status);
        return count;
    }

    public List<ListedMessageVo> listedMessage(Page page){
        IPage<ListedMessageVo> model=listedTranstionMapper.selectListedMessageVo(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ListedMessageVo>(page.getCurPage(), page.getPageSize()));
        List<ListedMessageVo> list=model.getRecords();
        for(ListedMessageVo item:list){
            if(item.getTimeLeft()<0){
                item.setTimeLeft(0);
            }
        }
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public List<ListedResultVo> listedResult(Page page, ListedResultReq req){
        IPage<ListedResultVo> model=listedTranstionMapper.selectListedResult(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ListedResultVo>(page.getCurPage(), page.getPageSize()),req);
        List<ListedResultVo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public List<ListedTranstionVo> backQryListedList(Page page, ListedTranstionVo req){
        IPage<ListedTranstionVo> model=listedTranstionMapper.backQryListedList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ListedTranstionVo>(page.getCurPage(), page.getPageSize()),req);
        List<ListedTranstionVo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }



    public List<ListedNoticeBirefVo> birefNotice(Page page){
        IPage<ListedNoticeBirefVo> model=listedNoticeInfoMapper.selectBirefNotice(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<ListedNoticeBirefVo>(page.getCurPage(), page.getPageSize()));
        List<ListedNoticeBirefVo> list = model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public ListedNoticeInfo noticeInfo(int info_id){
        ListedNoticeInfo model=listedNoticeInfoMapper.selectNoticeInfo(info_id);
        return model;
    }

    public boolean listedAdvisory(ListedAdvisoryReq req){
        String ckeckPhoneCaptchaCode= redisUtil.get(req.getPhone());
        if(ckeckPhoneCaptchaCode.equals(req.getCaptchaCode())){
            ListedAdvisory insert=new ListedAdvisory();
            insert.setName(req.getName());
            insert.setPhone(req.getPhone());
            insert.setRemark(req.getRemark());
            int count=listedAdvisoryMapper.insert(insert);
            if(count>0){
                return true;
            }
        }
        return false;
    }
}
