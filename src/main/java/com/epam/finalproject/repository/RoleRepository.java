package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;

import java.util.Optional;


public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

    int addRoleForUser(Role role, User user);

    int deleteRoleForUser(Role role, User user);
}
