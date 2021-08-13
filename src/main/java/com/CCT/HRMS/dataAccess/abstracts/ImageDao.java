package com.CCT.HRMS.dataAccess.abstracts;

import java.util.List;

import com.CCT.HRMS.entities.concretes.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Spring bean annotation to represent the database layer
public interface ImageDao extends JpaRepository<Image, Integer> {

    List<Image> getByResume_Id(int id);
}
