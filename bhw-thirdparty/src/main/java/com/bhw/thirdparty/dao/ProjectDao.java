package com.bhw.thirdparty.dao;

import com.bhw.thirdparty.pojo.ProjectBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository<ProjectBean,Integer> {


}
