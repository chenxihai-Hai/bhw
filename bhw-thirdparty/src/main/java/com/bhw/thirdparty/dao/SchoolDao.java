package com.bhw.thirdparty.dao;

import com.bhw.thirdparty.pojo.SchoolBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDao extends JpaRepository<SchoolBean,Integer> {


}
