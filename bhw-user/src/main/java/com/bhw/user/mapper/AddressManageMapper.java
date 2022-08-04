package com.bhw.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.user.pojo.AddressManage;
import com.bhw.user.pojo.RefundRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddressManageMapper extends BaseMapper<AddressManage> {

    IPage<AddressManage> qryAddressManageList(Page page, @Param("addressManage") AddressManage addressManage);

}
