package com.CCT.HRMS.business.abstracts.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.AboutForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.About;


public interface AboutService {

    Result add(AboutForAddDto aboutForAddDto);

    Result delete(int id);

    Result update(About about);

    DataResult<List<About>> getAll();

    // DataResult<About> getAboutById(int id);

    DataResult<List<About>> getAboutByResumeId(int id);
}
