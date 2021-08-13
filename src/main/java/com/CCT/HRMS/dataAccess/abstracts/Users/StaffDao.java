package com.CCT.HRMS.dataAccess.abstracts.Users;

import javax.transaction.Transactional;

import com.CCT.HRMS.entities.concretes.Users.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface StaffDao extends JpaRepository<Staff,Integer>,UserBaseDao<Staff>{
    
}
