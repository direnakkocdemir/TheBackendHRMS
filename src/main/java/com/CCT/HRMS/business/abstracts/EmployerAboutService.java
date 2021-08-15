package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.EmployerAboutDto;
import com.CCT.HRMS.entities.concretes.EmployerAbout;

/**
 * This interface class is for business layer of EmployerAbout
 * 
 * @author diren
 *
 */

public interface EmployerAboutService {

	Result add(EmployerAboutDto employerAboutDto);

	Result delete(int id);

	// Result update(EmployerAbout employerAbout);

	DataResult<List<EmployerAbout>> getAll();

	// DataResult<EmployerAbout> getEmployerAboutById(int id);

	DataResult<List<EmployerAbout>> getEmployerAboutByEmployerId(int id);
}
