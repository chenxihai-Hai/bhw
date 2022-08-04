package com.bhw.thirdparty.dao;

import com.bhw.thirdparty.pojo.BasicsTypeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicsTypeDao extends JpaRepository<BasicsTypeBean,Integer> {


}
