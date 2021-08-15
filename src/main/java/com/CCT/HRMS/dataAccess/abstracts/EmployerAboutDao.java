package com.CCT.HRMS.dataAccess.abstracts;

import java.util.List;

import com.CCT.HRMS.entities.concretes.EmployerAbout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // Spring bean annotation to represent the database layer
public interface EmployerAboutDao extends JpaRepository<EmployerAbout, Integer> {

    @Query("From EmployerAbout where employer_id=:id") // annotation declares finder queries directly on repository methods
    List<EmployerAbout> getEmployerAboutByEmployerId(int id);
}
