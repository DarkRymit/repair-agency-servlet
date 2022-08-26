package com.epam.finalproject.service;

import com.epam.finalproject.dto.WalletDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.request.AddMoneyRequest;

import java.util.List;

public interface WalletService {

    List<WalletDTO> findAllByUsername(String username);

    Page<WalletDTO> findAllByUsername(Pageable pageable, String username);

    WalletDTO findByNameAndUsername(String name, String username);

    Page<WalletDTO> findAll(Pageable pageable);

    WalletDTO findById(Long id);

    WalletDTO addMoney(AddMoneyRequest request);
}
