package com.CCT.HRMS.business.abstracts.Users;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.EmployerInfoDto;
import com.CCT.HRMS.entities.concretes.Users.Employer;

import java.util.*;

public interface EmployerService {

    Result add(Employer employer);

    Result delete(Employer employer);

    Result update(Employer employer);

    DataResult<List<Employer>> getAll();

    DataResult<Employer> getEmployerById(int id);

    Result existsById(int id);

    DataResult<EmployerInfoDto> getEmployerInfoById(int id);

}
