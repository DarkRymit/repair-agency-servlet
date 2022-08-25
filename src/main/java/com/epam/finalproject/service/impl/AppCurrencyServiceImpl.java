package com.epam.finalproject.service.impl;

import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.repository.AppCurrencyRepository;
import com.epam.finalproject.service.AppCurrencyService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.util.List;

@Service
public class AppCurrencyServiceImpl implements AppCurrencyService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AppCurrencyServiceImpl.class);
    AppCurrencyRepository appCurrencyRepository;

    ModelMapper modelMapper;

    public AppCurrencyServiceImpl(AppCurrencyRepository appCurrencyRepository, ModelMapper modelMapper) {
        this.appCurrencyRepository = appCurrencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AppCurrency> findAll() {
        return appCurrencyRepository.findAll();
    }

    @Override
    public AppCurrency findByCode(String code) {
        return appCurrencyRepository.findByCode(code).orElseThrow();
    }
}
