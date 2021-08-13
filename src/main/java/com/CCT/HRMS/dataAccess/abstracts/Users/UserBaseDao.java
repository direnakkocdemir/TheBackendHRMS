package com.CCT.HRMS.dataAccess.abstracts.Users;

import com.CCT.HRMS.entities.concretes.Users.User;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;


@NoRepositoryBean
public interface UserBaseDao<T extends User> extends Repository<T, Integer> {

    T findById(int id);

    <T extends User> T save(T entity);

    <T extends User> T getUserByEmail(String email);

    // T findOne(Long id);

    // Iterable<T> findAll();

    // Iterable<T> findAll(Sort sort);

    // Page<T> findAll(Pageable pageable);

    // <T extends User> T saveAndFlush(T entity);

    // <T extends User> T delete(T entity);

    // <T extends User> T getById(int id);

    // List<T> findAll();

}
