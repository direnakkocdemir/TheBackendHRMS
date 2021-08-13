package com.CCT.HRMS.business.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.SkillForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Skill;

public interface SkillService {
    
    Result add(SkillForAddDto skillForAddDto);

    Result delete(int id);

    Result update(Skill skill);

    DataResult<List<Skill>> getAll();

    // DataResult<Skill> getSkillById(int id);

    DataResult<List<Skill>> getSkillByResumeId(int id);
}
