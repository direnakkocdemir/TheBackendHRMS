package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.ResumeDto;
import com.CCT.HRMS.entities.DTOs.UpdateResumeDto;
import com.CCT.HRMS.entities.concretes.Resume;

/**
 * This interface class is for business layer of Resume
 * 
 * @author diren
 *
 */

public interface ResumeService {

	Result add(int jobseekerId);

	// Result delete(Resume resume);

	Result update(Resume resume);

	DataResult<List<Resume>> getAll();

	// DataResult<Resume> getResumeById(int id);

	DataResult<ResumeDto> getResumeByJobseekerId(int id);

	DataResult<Integer> getIdByJobseekerId(int id);
}
