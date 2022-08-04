package com.bhw.serverManage.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bhw.common.base.Response;
import com.bhw.serverManage.mapper.*;
import com.bhw.serverManage.pojo.IntellectualTypeBean;
import com.bhw.serverManage.vo.AiListVo;
import com.bhw.serverManage.vo.OrderVo;
import com.bhw.serverManage.vo.RequireVo;
import com.bhw.serverManage.pojo.ServerProjectBean;
import com.bhw.serverManage.vo.TechAchievementVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class ProjectDwService  {

    Logger logger = LoggerFactory.getLogger(ProjectDwService.class);

    @Resource
    private IntellectualMapper intellectualMapper;

    @Resource
    private IntellectualTypeMapper intellectualTypeMapper;

    @Resource
    private RequireMapper requireMapper;

    @Resource
    ServiceDealMapper serviceDealMapper;

    @Resource
    ServiceDwMapper serviceDwMapper;

    public void addIntellectuaProject(ServerProjectBean serverProjectBean){
        intellectualMapper.insert(serverProjectBean);
    }

    public void batchUpStatus(String pIds,Integer status){
        String[] split = pIds.split(",");
        for (String pId: split) {
            ServerProjectBean serverProjectBean = new ServerProjectBean();
            serverProjectBean.setId(Integer.valueOf(pId));
            serverProjectBean.setStatus(status);
            intellectualMapper.updateById(serverProjectBean);
        }
    }

    public void batchUpBoutique(String pIds,Integer status){
        String[] split = pIds.split(",");
        for (String pId: split) {
            ServerProjectBean serverProjectBean = new ServerProjectBean();
            serverProjectBean.setId(Integer.valueOf(pId));
            serverProjectBean.setBoutique(status);
            intellectualMapper.updateById(serverProjectBean);
        }
    }

    public void batchUpAuditStatus(String pIds,Integer status){
        String[] split = pIds.split(",");
        for (String pId: split) {
            ServerProjectBean serverProjectBean = new ServerProjectBean();
            serverProjectBean.setId(Integer.valueOf(pId));
            serverProjectBean.setAuditStatus(status);
            intellectualMapper.updateById(serverProjectBean);
        }
    }

    public List<ServerProjectBean> qryIntellectuaProject(ServerProjectBean serverProjectBean, com.bhw.common.base.Page page){
        String priceFilter = serverProjectBean.getPriceFilter();
        if(StringUtils.isNotEmpty(priceFilter)){
            String[] split = priceFilter.split("-");
            if(split.length<2){
                serverProjectBean.setPriceFilter(split[0]);
            }else{
                serverProjectBean.setPriceFilter(split[0]);
                serverProjectBean.setPriceFilterEnd(split[1]);
            }
        }
        Page mPage = new Page();
        mPage.setCurrent(page.getCurPage());
        mPage.setSize(page.getPageSize());
        IPage<ServerProjectBean> serverProjectBeanIPage = intellectualMapper.qryIntellectuaProject(mPage, serverProjectBean);
        List<ServerProjectBean>  list = serverProjectBeanIPage.getRecords();
        page.setCounts((int)serverProjectBeanIPage.getTotal(),page.getPageSize());
        page.setCurPage((int)serverProjectBeanIPage.getCurrent());
        return list;
    }

    public List<OrderVo> qryIntellectuaOrderList(OrderVo orderVo, com.bhw.common.base.Page page){
        IPage<OrderVo> qryIntellectuaOrderList = intellectualMapper.qryIntellectuaOrderList(new Page<OrderVo>(page.getCurPage(), page.getPageSize()), orderVo);
        List<OrderVo> records = qryIntellectuaOrderList.getRecords();
        page.setCounts((int)qryIntellectuaOrderList.getTotal(),page.getPageSize());
        page.setCurPage((int)qryIntellectuaOrderList.getCurrent());
        return records;
    }

    public List<OrderVo> backQryOrderList(OrderVo orderVo, com.bhw.common.base.Page page){
        IPage<OrderVo> backQryOrderList = intellectualMapper.backQryOrderList(new Page<OrderVo>(page.getCurPage(), page.getPageSize()), orderVo);
        List<OrderVo> records = backQryOrderList.getRecords();
        page.setCounts((int)backQryOrderList.getTotal(),page.getPageSize());
        page.setCurPage((int)backQryOrderList.getCurrent());
        return records;
    }

    public List<IntellectualTypeBean> qryIntellectualType(IntellectualTypeBean intellectualTypeBean){
        Wrapper<IntellectualTypeBean> queryWrapper =  new QueryWrapper<IntellectualTypeBean>(intellectualTypeBean);
        return intellectualTypeMapper.selectList(queryWrapper);

    }

    public List<RequireVo> qryRequireList(RequireVo requireVo, com.bhw.common.base.Page page){
        IPage<RequireVo> selectRequireList = requireMapper.selectRequireList(new Page<RequireVo>(page.getCurPage(), page.getPageSize()), requireVo);
        List<RequireVo> records = selectRequireList.getRecords();
        page.setCounts((int)selectRequireList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectRequireList.getCurrent());
        return records;
    }

    public List<AiListVo> selectServiceDwList(AiListVo requireVo, com.bhw.common.base.Page page){
        IPage<AiListVo> selectRequireList = serviceDwMapper.selectServiceDwList(new Page<AiListVo>(page.getCurPage(), page.getPageSize()), requireVo);
        List<AiListVo> records = selectRequireList.getRecords();
        page.setCounts((int)selectRequireList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectRequireList.getCurrent());
        return records;
    }

    public List<RequireVo> qryScienceRequireList(RequireVo requireVo, com.bhw.common.base.Page page){
        IPage<RequireVo> selectRequireList = requireMapper.selectScienceRequireList(new Page<RequireVo>(page.getCurPage(), page.getPageSize()), requireVo);
        List<RequireVo> records = selectRequireList.getRecords();
        page.setCounts((int)selectRequireList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectRequireList.getCurrent());
        return records;
    }

    public List<RequireVo> qryServiceDealList(RequireVo requireVo, com.bhw.common.base.Page page){
        IPage<RequireVo> selectRequireList = serviceDealMapper.selectServiceDealList(new Page<RequireVo>(page.getCurPage(), page.getPageSize()), requireVo);
        List<RequireVo> records = selectRequireList.getRecords();
        page.setCounts((int)selectRequireList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectRequireList.getCurrent());
        return records;
    }

    public List<TechAchievementVo> qryTechAchievemnetList(TechAchievementVo requireVo, com.bhw.common.base.Page page){
        IPage<TechAchievementVo> selectRequireList = serviceDwMapper.qryTechAchievemnetList(new Page<TechAchievementVo>(page.getCurPage(), page.getPageSize()), requireVo);
        List<TechAchievementVo> records = selectRequireList.getRecords();
        page.setCounts((int)selectRequireList.getTotal(),page.getPageSize());
        page.setCurPage((int)selectRequireList.getCurrent());
        return records;
    }
}
