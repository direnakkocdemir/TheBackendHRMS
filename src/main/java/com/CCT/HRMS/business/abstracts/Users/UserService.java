package com.CCT.HRMS.business.abstracts.Users;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.concretes.Users.User;

/**
 * This interface class is for business layer of User
 * 
 * @author diren
 *
 */


public interface UserService {

    // Result add(User user);

    // Result delete(User user);

    // Result update(User user);

    // DataResult<List<User>> getAll();

    // DataResult<User> getUserById(int id);

    DataResult<User> getUserByEmail(String email);

    Result checkUser(String email);

}
