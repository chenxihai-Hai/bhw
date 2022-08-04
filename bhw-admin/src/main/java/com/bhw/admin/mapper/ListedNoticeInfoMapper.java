package com.bhw.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.pojo.ListedNoticeInfo;
import com.bhw.admin.vo.ListedNoticeBirefVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ListedNoticeInfoMapper extends BaseMapper<ListedNoticeInfo> {

    public IPage<ListedNoticeBirefVo> selectBirefNotice(Page page);

    public ListedNoticeInfo selectNoticeInfo(int info_id);
}
