package com.CCT.HRMS.dataAccess.abstracts.Users;

import javax.transaction.Transactional;

import com.CCT.HRMS.entities.concretes.Users.User;


@Transactional
public interface UserDao extends UserBaseDao<User> {

    User getUserByEmail(String email);

    boolean existsByEmail(String email);
}