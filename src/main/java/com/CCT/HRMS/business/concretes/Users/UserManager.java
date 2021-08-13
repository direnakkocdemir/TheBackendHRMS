package com.CCT.HRMS.business.concretes.Users;

import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.Users.UserDao;
import com.CCT.HRMS.entities.concretes.Users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class UserManager implements UserService {

    /// Properties
    private UserDao userDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    // @Override
    // public Result add(User user) {
    // this.userDao.save(user);
    // return new SuccessResult(Messages.UserIsAdded);
    // }

    // @Override
    // public Result delete(User user) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    // @Override
    // public Result update(User user) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    // @Override
    // public DataResult<List<User>> getAll() {
    // return new SuccessDataResult<List<User>>(this.userDao.findAll());
    // }

    // @Override
    // public DataResult<User> getUserById(int id) {
    // return new SuccessDataResult<User>(this.userDao.getById(id));
    // }

    /**
     * Getting a User information by email
     */
    @Override
    public DataResult<User> getUserByEmail(String email) {
        User user = this.userDao.getUserByEmail(email);
        if (user != null) {
            return new SuccessDataResult<>(user);
        }
        return new ErrorDataResult<>(Messages.UserNotFound);
    }

    @Override
    public Result checkUser(String email) {
        
        if(userDao.existsByEmail(email)){
            return new SuccessResult();
        }
        return new ErrorResult(Messages.UserNotExist);
    }

}
