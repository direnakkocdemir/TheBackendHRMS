package com.CCT.HRMS.business.abstracts.Validations;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.concretes.Validations.Verification;

public interface VerificationService {
    
    Result add(Verification verification);

    Result delete(Verification verification);

    Result update(Verification verification);

    DataResult<List<Verification>> getAll();

    DataResult<Verification> getById(int id);
}
