package com.bhw.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.CollegeMapper;
import com.bhw.admin.pojo.SchoolBean;
import com.bhw.admin.vo.CollegesVo;
import com.bhw.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CollegeService {

    @Resource
    CollegeMapper collegeMapper;

    public int getCollegeCount(Integer type){
        int count=collegeMapper.selectCollegeCountByType(type);
        return count;
    }

    public List<CollegesVo> getColleges(Integer type, Page page){
        IPage<CollegesVo> model=collegeMapper.selectCollegeByType(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<CollegesVo>(page.getCurPage(),page.getPageSize()),type);
        List<CollegesVo> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }

    public List<SchoolBean> qryCollegeList(SchoolBean schoolBean, Page page){
        IPage<SchoolBean> model=collegeMapper.selectCollegeList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<SchoolBean>(page.getCurPage(),page.getPageSize()),schoolBean);
        List<SchoolBean> list=model.getRecords();
        page.setCounts((int)model.getTotal(),page.getPageSize());
        page.setCurPage((int)model.getCurrent());
        return list;
    }
}
