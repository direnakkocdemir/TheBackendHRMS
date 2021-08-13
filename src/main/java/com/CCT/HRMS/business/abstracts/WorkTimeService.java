package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.WorkTimeDto;
import com.CCT.HRMS.entities.concretes.WorkTime;

public interface WorkTimeService {
    
    Result add(WorkTime workTime);

    Result delete(WorkTime workTime);

    Result update(WorkTime workTime);

    DataResult<List<WorkTime>> getAll();

    DataResult<List<WorkTimeDto>> getAllForDropdown();

    DataResult<WorkTime> getWorkTimeById(int id);
}
