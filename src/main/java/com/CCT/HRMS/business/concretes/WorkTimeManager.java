package com.CCT.HRMS.business.concretes;

import java.util.ArrayList;
import java.util.List;

import com.CCT.HRMS.business.abstracts.WorkTimeService;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.WorkTimeDao;
import com.CCT.HRMS.entities.DTOs.WorkTimeDto;
import com.CCT.HRMS.entities.concretes.WorkTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class WorkTimeManager implements WorkTimeService {

    // Properties
    private WorkTimeDao workTimeDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public WorkTimeManager(WorkTimeDao workTimeDao) {
        this.workTimeDao = workTimeDao;
    }
    /**
     * Adding a Worktime to the database
     */
    @Override
    public Result add(WorkTime workTime) {

        workTimeDao.save(workTime);
        return new SuccessResult();
    }
    /**
     * 
     */
    @Override
    public Result delete(WorkTime workTime) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 
     */
    @Override
    public Result update(WorkTime workTime) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Getting all the WorkTime information from the system
     */
    @Override
    public DataResult<List<WorkTime>> getAll() {
        List<WorkTime> workTimes = workTimeDao.findAll();
        if (workTimes.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(workTimes);
    }
    /**
     * Getting WorkTime information by id from the system
     */
    @Override
    public DataResult<WorkTime> getWorkTimeById(int id) {
        WorkTime workTime = workTimeDao.getById(id);
        if (workTime != null) {
            return new SuccessDataResult<>(workTime);
        }
        return new ErrorDataResult<>();
    }
    /**
     * 
     */
    @Override
    public DataResult<List<WorkTimeDto>> getAllForDropdown() {
        List<WorkTime> workTimes = workTimeDao.findAll();
        List<WorkTimeDto> workTimeDtos = new ArrayList<>();
        int key = 1;
        for (WorkTime workTime : workTimes) {
            WorkTimeDto workTimeDto = new WorkTimeDto();
            workTimeDto.setKey(key);
            key++;
            workTimeDto.setText(workTime.getName());
            workTimeDto.setValue(workTime.getId());
            workTimeDtos.add(workTimeDto);
        }
        if (workTimeDtos.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(workTimeDtos);
    }

}
