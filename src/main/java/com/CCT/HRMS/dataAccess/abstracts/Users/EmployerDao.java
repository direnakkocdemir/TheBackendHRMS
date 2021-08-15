package com.CCT.HRMS.dataAccess.abstracts.Users;

import javax.transaction.Transactional;

import com.CCT.HRMS.entities.concretes.Users.Employer;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional // The annotation to configure the transactional behavior of a method
public interface EmployerDao extends JpaRepository<Employer, Integer>, UserBaseDao<Employer> {

}
