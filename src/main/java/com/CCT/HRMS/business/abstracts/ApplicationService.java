package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.concretes.Application;

/**
 * This interface class is for business layer of Application
 * 
 * @author diren
 *
 */

public interface ApplicationService {

	Result add(Application application);

	// Result delete(EmployerAbout employerAbout);

	// Result update(EmployerAbout employerAbout);

	DataResult<List<Application>> getAll();

	DataResult<List<Application>> getAllByAdvertisementId(int id);

	DataResult<List<Application>> getAllByJobseekerId(int id);
}
