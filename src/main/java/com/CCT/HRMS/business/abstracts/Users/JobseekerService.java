package com.CCT.HRMS.business.abstracts.Users;

import java.util.*;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.JobseekerInfoDto;
import com.CCT.HRMS.entities.concretes.Users.Jobseeker;

/**
 * This interface class is for business layer of Jobseeker
 * 
 * @author diren
 *
 */


public interface JobseekerService {

    Result add(Jobseeker jobseeker);

    Result delete(Jobseeker jobseeker);

    Result update(Jobseeker jobseeker);

    DataResult<List<Jobseeker>> getAll();

    DataResult<Jobseeker> getJobseekerById(int id);

    Result existsById(int id);

    DataResult<JobseekerInfoDto> getJobseekerInfoById(int id);
}
