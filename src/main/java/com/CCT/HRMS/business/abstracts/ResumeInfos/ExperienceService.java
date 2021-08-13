package com.CCT.HRMS.business.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.ExperienceForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;

public interface ExperienceService {
    
    Result add(ExperienceForAddDto experienceForAddDto);

    Result delete(int id);

    Result update(Experience experience);

    DataResult<List<Experience>> getAll();

    // DataResult<Experience> getExperienceById(int id);

    DataResult<List<Experience>> getExperienceByResumeId(int id);
}
