package com.bhw.thirdparty.service;

import com.bhw.thirdparty.dao.*;
import com.bhw.thirdparty.pojo.*;
import com.bhw.thirdparty.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class UserService {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    BasicsTypeDao basicsTypeDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SchoolDao schoolDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    PatentDao patentDao;

    @Autowired
    OutComeDao outComeDao;

    @Autowired
    OrganizationDao organizationDao;

    public List<UserBean> test(){
        return userDao.findAll();
    }

    public List<UserBean> qryUserList(UserBean userBean){
        List<Object[]> list = null;
       /* StringBuffer sql = new StringBuffer("SELECT t.USERNAME,t.PHOTO,tb.NAME AS brokerName,tb1.NAME AS dutyName,tb2.NAME AS professionName FROM T_USER t ");
        sql.append(" LEFT JOIN T_BASICS_TYPE tb ON tb.ID = t.BROKERID ").append(" LEFT JOIN T_BASICS_TYPE tb1 ON tb1.ID = t.DUTYID ").append(" LEFT JOIN T_BASICS_TYPE tb2 ON tb2.ID = t.PROFESSIONID ");
        sql.append(" WHERE 1=1 ");
        if(null != userBean.getType()){
            sql.append(" AND t.TYPE= "+userBean.getType());
        }
        sql.append(" ORDER BY t.CREATETIME DESC ");
        Query query = em.createQuery(sql.toString(),UserBean.class);
        list = query.getResultList();*/
        /*Example<UserBean> example = Example.of(userBean);
        userDao.findAll(example,Sort.by(Sort.Direction.DESC,"createTime"));*/
        list = userDao.qryUserList(userBean.getType());
        List<UserBean> vos = EntityUtils.castEntity(list,UserBean.class);
        return vos;
    }

    public List<UserBean> qryUserListByTypeName(String param){
        List<Object[]> list = userDao.qryUserListByTypeName(param,param);
        List<UserBean> vos = EntityUtils.castEntity(list,UserBean.class);
        return vos;
    }

    public List<ProjectBean> qryProjectList(ProjectBean projectBean){
        Example<ProjectBean> example = Example.of(projectBean);
        return projectDao.findAll(example,Sort.by(Sort.Direction.DESC,"id"));
    }

    public List<SchoolBean> qrySchoolList(SchoolBean schoolBean){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<SchoolBean> example = Example.of(schoolBean,matcher);
        return schoolDao.findAll(example,Sort.by(Sort.Direction.DESC,"id"));
    }

    public List<OutComeBean> qryOutComeList(OutComeBean outComeBean){
        Example<OutComeBean> example = Example.of(outComeBean);
        return outComeDao.findAll(example,Sort.by(Sort.Direction.DESC,"id"));
    }

    public List<OrganizationBean> qryOrganizationList(OrganizationBean bean){
        Example<OrganizationBean> example = Example.of(bean);
        return organizationDao.findAll(example,Sort.by(Sort.Direction.DESC,"id"));
    }

    public void saveOutCome(List<OutComeBean> list){
        outComeDao.saveAll(list);
    }

    public void saveOutCome(OutComeBean outComeBean){
        outComeDao.save(outComeBean);
    }
    public void saveProject(ProjectBean projectBean){
        projectDao.save(projectBean);
    }

    public void saveSchool(SchoolBean schoolBean){
        schoolDao.save(schoolBean);
    }

    public int saveUserInfo(UserBean userBean){
        return userDao.saveAndFlush(userBean).getId();
    }

    public int saveBasicsType(BasicsTypeBean basicsTypeBean){
        Example<BasicsTypeBean> example = Example.of(basicsTypeBean);
        List<BasicsTypeBean> list =  basicsTypeDao.findAll(example);
        if(null != list && !list.isEmpty()){
            return list.get(0).getId();
        }
        return basicsTypeDao.saveAndFlush(basicsTypeBean).getId();
    }

    public void savePatent(PatentBean patentBean){patentDao.save(patentBean);}


    public List<PatentBean> qryPatentList(PatentBean projectBean){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("patentname", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<PatentBean> example = Example.of(projectBean,matcher);
        return patentDao.findAll(example,Sort.by(Sort.Direction.DESC,"id"));
    }
}
