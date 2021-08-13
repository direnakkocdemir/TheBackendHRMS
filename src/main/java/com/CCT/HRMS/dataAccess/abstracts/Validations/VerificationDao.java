package com.CCT.HRMS.dataAccess.abstracts.Validations;

import com.CCT.HRMS.entities.concretes.Validations.Verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VerificationDao extends JpaRepository<Verification,Integer> {
    
}
