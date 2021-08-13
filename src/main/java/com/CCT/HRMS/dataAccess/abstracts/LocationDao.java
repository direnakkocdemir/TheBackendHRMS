package com.CCT.HRMS.dataAccess.abstracts;

import com.CCT.HRMS.entities.concretes.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Spring bean annotation to represent the database layer
public interface LocationDao extends JpaRepository<Location, Integer> {

}
