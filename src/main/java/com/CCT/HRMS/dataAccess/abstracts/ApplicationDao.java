package com.CCT.HRMS.dataAccess.abstracts;

import java.util.List;

import com.CCT.HRMS.entities.concretes.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Spring bean annotation to represent the database layer
public interface ApplicationDao extends JpaRepository<Application, Integer> {

    List<Application> getByAdvertisement_Id(int id);

    List<Application> getByJobseeker_Id(int id);
}
