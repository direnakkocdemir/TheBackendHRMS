package com.CCT.HRMS.business.concretes.Users;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Users.StaffService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.Users.StaffDao;
import com.CCT.HRMS.entities.concretes.Users.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class StaffManager implements StaffService {

    // Properties
    private StaffDao staffDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public StaffManager(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    /**
     * Adding a staff the system
     */
    @Override
    public Result add(Staff staff) {
        var result = BusinessRules.run(checkNullInformationForStaff(staff));
        if (result.isSuccess()) {
            staffDao.save(staff);
            return new SuccessResult(Messages.StaffAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Deleting a staff from the system
     */
    @Override
    public Result delete(Staff staff) {
        Staff staffToDelete = staffDao.getById(staff.getId());
        staffDao.delete(staffToDelete);
        return new SuccessResult(Messages.StaffDeleted);
    }

    /**
     * Updating the staff
     */
    @Override
    public Result update(Staff staff) {
        Staff staffToUpdate = staffDao.getById(staff.getId());
        staffToUpdate.setFirstName(staff.getFirstName());
        staffToUpdate.setLastName(staff.getLastName());
        staffToUpdate.setEmail(staff.getEmail());
        staffToUpdate.setPassword(staff.getPassword());
        staffDao.saveAndFlush(staffToUpdate);
        return new SuccessResult(Messages.StaffUpdated);
    }

    /**
     * Getting all of the staffs
     */
    @Override
    public DataResult<List<Staff>> getAll() {
        List<Staff> staffs = staffDao.findAll();
        return new SuccessDataResult<List<Staff>>(staffs, Messages.StaffsListed);
    }

    /**
     * Getting the staff who has defined id
     */
    @Override
    public DataResult<Staff> getStaffById(int id) {
        Staff staff = staffDao.getById(id);
        if (staff != null) {
            return new SuccessDataResult<Staff>(staff);
        }
        return new ErrorDataResult<Staff>(Messages.StaffNotFound);
    }

    // Validations

    private Result checkNullInformationForStaff(Staff staff) {
        if (staff.getFirstName() != null && staff.getLastName() != null && staff.getEmail() != null
                && staff.getCreated_at() != null) {
            return new SuccessResult();
        }
        return new ErrorResult();
    }

    @Override
    public Result existsById(int id) {
        if (staffDao.getById(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult();
    }
}
