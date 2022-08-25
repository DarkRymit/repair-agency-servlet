package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.search.*;
import com.epam.finalproject.repository.ReceiptRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.request.search.*;
import com.epam.finalproject.service.SearchService;
import com.epam.finalproject.util.SearchRequestResolver;
import org.modelmapper.ModelMapper;

@Service
public class SearchServiceImpl implements SearchService {

    ReceiptRepository receiptRepository;

    UserRepository userRepository;

    SearchRequestResolver searchRequestResolver;

    ModelMapper modelMapper;

    public SearchServiceImpl(ReceiptRepository receiptRepository, UserRepository userRepository,
            SearchRequestResolver searchRequestResolver, ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.userRepository = userRepository;
        this.searchRequestResolver = searchRequestResolver;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ReceiptDTO> findBySearch(ReceiptSearch receiptSearch) {
        return receiptRepository.findAll(receiptSearch)
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class));

    }

    @Override
    public Page<ReceiptDTO> findBySearch(ReceiptSearchRequest receiptSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest));
    }

    @Override
    public Page<ReceiptDTO> findBySearch(ReceiptWithCustomerSearch receiptSearch) {
        return receiptRepository.findAll(receiptSearch)
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class));
    }

    @Override
    public Page<ReceiptDTO> findBySearch(ReceiptWithCustomerSearchRequest receiptSearchRequest, String customer) {
        User user = userRepository.findByUsername(customer).orElseThrow();
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest, user));
    }

    @Override
    public Page<ReceiptDTO> findBySearch(ReceiptWithMasterSearch receiptSearch) {
        return receiptRepository.findAll(receiptSearch)
                .map(receipt -> modelMapper.map(receipt, ReceiptDTO.class));
    }

    @Override
    public Page<ReceiptDTO> findBySearch(ReceiptWithMasterSearchRequest receiptSearchRequest, String master) {
        User user = userRepository.findByUsername(master).orElseThrow();
        return findBySearch(searchRequestResolver.resolve(receiptSearchRequest, user));
    }

    @Override
    public Page<UserDTO> findBySearch(UserSearch userSearch) {
        return userRepository.findAll(userSearch)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Page<UserDTO> findBySearch(UserSearchRequest userSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(userSearchRequest));
    }

    @Override
    public Page<UserDTO> findBySearch(MasterSearch masterSearch) {
        return userRepository.findAll(masterSearch)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Page<UserDTO> findBySearch(MasterSearchRequest masterSearchRequest) {
        return findBySearch(searchRequestResolver.resolve(masterSearchRequest));
    }
}
