package com.CCT.HRMS.dataAccess.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // Spring bean annotation to represent the database layer
public interface LanguageDao extends JpaRepository<Language, Integer> {

	@Query("From Language where resume_id=:id") // annotation declares finder queries directly on repository methods
	List<Language> getLanguageByResumeId(int id);
}
