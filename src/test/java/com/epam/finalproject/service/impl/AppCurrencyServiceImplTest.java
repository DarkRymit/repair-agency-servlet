package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.AppCurrencyRepository;
import com.epam.finalproject.request.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppCurrencyServiceImplTest {

    AppCurrencyServiceImpl appCurrencyService;

    @Mock
    AppCurrencyRepository repository;

    final static ModelMapper modelMapper;

    List<AppCurrency> currencies;

    static {
        modelMapper = new ModelMapper();
    }

    @BeforeEach
    void setUp() {
        currencies = List.of(new AppCurrency(1L,"USD"),new AppCurrency(2L,"UAH"));
        appCurrencyService = new AppCurrencyServiceImpl(repository,modelMapper);
    }

    @Test
    void findAllShouldReturnCorrectListWhenListExist() {
        when(repository.findAll()).thenReturn(currencies);
        assertEquals(currencies,appCurrencyService.findAll());
    }

    @Test
    void findByCodeShouldReturnCorrectObjectWhenByExistCode() {
        when(repository.findByCode("USD")).thenReturn(Optional.ofNullable(currencies.get(0)));
        assertEquals(currencies.get(0),appCurrencyService.findByCode("USD"));
        assertEquals("USD",appCurrencyService.findByCode("USD").getCode());
        assertEquals(1L,appCurrencyService.findByCode("USD").getId());
    }
    @Test
    void findByCodeShouldThrowNoSuchElementExceptionWhenByNonExistCode() {
        when(repository.findByCode("GGG")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,() -> appCurrencyService.findByCode("GGG"));
    }
}