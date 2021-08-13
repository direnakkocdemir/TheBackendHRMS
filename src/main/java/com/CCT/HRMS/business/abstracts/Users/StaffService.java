package com.CCT.HRMS.business.abstracts.Users;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.concretes.Users.Staff;

import java.util.*;

public interface StaffService {
    
    Result add(Staff staff);

    Result delete(Staff staff);

    Result update(Staff staff);

    DataResult<List<Staff>> getAll();

    DataResult<Staff> getStaffById(int id);

    Result existsById(int id);
    
}
