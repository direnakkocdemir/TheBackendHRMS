package com.CCT.HRMS.dataAccess.abstracts.Users;

import com.CCT.HRMS.entities.concretes.Users.User;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;


@NoRepositoryBean
public interface UserBaseDao<T extends User> extends Repository<T, Integer> {

    T findById(int id);

    <T extends User> T save(T entity);

    <T extends User> T getUserByEmail(String email);
    
    <T extends User> T findByEmail(String email);

}
