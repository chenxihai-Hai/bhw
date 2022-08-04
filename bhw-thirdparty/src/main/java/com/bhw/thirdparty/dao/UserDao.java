package com.bhw.thirdparty.dao;

import com.bhw.thirdparty.pojo.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao  extends JpaRepository<UserBean,Integer> {

    /*@Query(value="SELECT t.USERNAME,t.PHOTO,tb.NAME AS brokerName,tb1.NAME AS dutyName,tb2.NAME AS professionName FROM T_USER t " +
            " LEFT JOIN T_BASICS_TYPE tb ON tb.ID = t.BROKERID" +
            " LEFT JOIN T_BASICS_TYPE tb1 ON tb1.ID = t.DUTYID" +
            " LEFT JOIN T_BASICS_TYPE tb2 ON tb2.ID = t.PROFESSIONID" +
            " WHERE 1=1 AND t.TYPE=#{#userBean.type} ORDER BY t.CREATETIME DESC",nativeQuery = true)*/
    @Query(value="select t.USERNAME as userName,t.PHOTO as photo,tb1.NAME AS dutyName,tb2.NAME AS professionName,tb.NAME AS brokerName from T_USER t LEFT JOIN T_BASICS_TYPE tb ON tb.ID = t.BROKERID " +
            " LEFT JOIN T_BASICS_TYPE tb1 ON tb1.ID = t.DUTYID LEFT JOIN T_BASICS_TYPE tb2 ON tb2.ID = t.PROFESSIONID" +
            " WHERE 1=1 AND t.TYPE=?1 ORDER BY t.CREATETIME DESC",nativeQuery = true)
    public List<Object[]> qryUserList(Integer type);

    @Query(value="SELECT t.USERNAME AS title,t.PHOTO AS avatar,tb.NAME AS summary " +
            " FROM T_USER t LEFT JOIN T_BASICS_TYPE tb ON (t.BROKERID = tb.ID OR t.`PROFESSIONID` = tb.`ID` OR t.`BROKERID` = tb.`ID`) " +
            " WHERE 1=1 AND tb.`NAME` like CONCAT('%',?1,'%') OR t.`USERNAME` LIKE CONCAT('%',?2,'%') ORDER BY t.CREATETIME DESC",nativeQuery = true)
    public List<Object[]> qryUserListByTypeName(String type,String type2);

}
