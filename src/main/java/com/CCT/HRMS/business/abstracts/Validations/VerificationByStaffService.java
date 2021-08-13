package com.CCT.HRMS.business.abstracts.Validations;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.concretes.Validations.VerificationByStaff;

public interface VerificationByStaffService {

    Result add(VerificationByStaff verificationByStaff);

    Result delete(VerificationByStaff verificationByStaff);

    Result update(VerificationByStaff verificationByStaff);

    DataResult<List<VerificationByStaff>> getAll();

    DataResult<VerificationByStaff> getById(int id);
}