package com.CCT.HRMS.business.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.EducationForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Education;

public interface EducationService {

    Result add(EducationForAddDto educationForAddDto);

    Result delete(int id);

    Result update(Education education);

    DataResult<List<Education>> getAll();

    // DataResult<Education> getEducationById(int id);

    DataResult<List<Education>> getEducationByResumeId(int id);
}
