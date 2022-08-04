package com.bhw.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.mapper.RefundRecordMapper;
import com.bhw.user.mapper.UserOrderMapper;
import com.bhw.user.pojo.RefundRecord;
import com.bhw.user.vo.UserOrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RefundRecordService {

    @Resource
    private RefundRecordMapper refundRecordMapper;

    public List<RefundRecord> qryRefundRecordList(RefundRecord refundRecord, com.bhw.common.base.Page page){
        IPage<RefundRecord> refundRecordIPage = refundRecordMapper.qryRefundRecordList(new Page<RefundRecord>(page.getCurPage(), page.getPageSize()),refundRecord);
        List<RefundRecord> list = refundRecordIPage.getRecords();
        page.setCounts((int)refundRecordIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)refundRecordIPage.getCurrent());
        return list;
    }

    public RefundRecord getRefundRecord(RefundRecord refundRecord){
        return refundRecordMapper.selectById(refundRecord.getId());
    }

    public void saveRefundRecord(RefundRecord refundRecord){
        refundRecordMapper.insert(refundRecord);
    }
}
