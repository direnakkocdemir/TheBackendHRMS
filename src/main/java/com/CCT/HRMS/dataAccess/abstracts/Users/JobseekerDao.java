package com.CCT.HRMS.dataAccess.abstracts.Users;

import javax.transaction.Transactional;

import com.CCT.HRMS.entities.concretes.Users.Jobseeker;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface JobseekerDao extends JpaRepository<Jobseeker,Integer>,UserBaseDao<Jobseeker>{
 
    
}
