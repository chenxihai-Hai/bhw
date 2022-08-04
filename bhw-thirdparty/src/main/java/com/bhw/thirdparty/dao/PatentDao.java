package com.bhw.thirdparty.dao;

import com.bhw.thirdparty.pojo.PatentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatentDao extends JpaRepository<PatentBean,Integer> {


}
