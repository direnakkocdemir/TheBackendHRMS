package com.CCT.HRMS.dataAccess.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository //Spring bean annotation to represent the database layer
public interface ExperienceDao extends JpaRepository<Experience,Integer>{
    
    @Query("From Experience where resume_id=:id")
    List<Experience> getExperienceByResumeId(int id);
}
