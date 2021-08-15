package com.CCT.HRMS.dataAccess.abstracts;

import com.CCT.HRMS.entities.concretes.Resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // Spring bean annotation to represent the database layer
public interface ResumeDao extends JpaRepository<Resume, Integer> {

    @Query("From Resume where jobseeker_id =:id") // annotation declares finder queries directly on repository methods
    Resume getByJobseeker_Id(int id);

    @Query("Select id From Resume where jobseeker_id=:id")
    int getIdByJobseeker_Id(int id);

}
