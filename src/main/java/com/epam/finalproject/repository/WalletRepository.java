package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.Wallet;

import java.util.List;
import java.util.Optional;


public interface WalletRepository extends PagingAndSortingRepository<Wallet, Long> {

    List<Wallet> findAllByUser_Username(String username);

    Page<Wallet> findAllByUser_Username(Pageable pageable, String username);

    Optional<Wallet> findDistinctByNameAndUser_Username(String name, String username);

}
