package com.CCT.HRMS.business.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.LanguageForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;

public interface LanguageService {
    
    Result add(LanguageForAddDto languageForAddDto);

    Result delete(int id);

    Result update(Language language);

    DataResult<List<Language>> getAll();

    // DataResult<Language> getLanguageById(int id);

    DataResult<List<Language>> getLanguageByResumeId(int id);
}
