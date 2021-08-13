package com.CCT.HRMS.dataAccess.abstracts.Validations;

import com.CCT.HRMS.entities.concretes.Validations.VerificationByStaff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VerificationByStaffDao extends JpaRepository<VerificationByStaff,Integer> {
    
}
