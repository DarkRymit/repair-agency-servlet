package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.search.MasterSearch;
import com.epam.finalproject.model.search.UserSearch;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Page<User> findAll(MasterSearch masterSearch);

    Page<User> findAll(UserSearch userSearch);
}
