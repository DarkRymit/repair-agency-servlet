package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.RepairCategory;
import com.epam.finalproject.repository.RepairCategoryRepository;
import com.epam.finalproject.service.RepairCategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairCategoryServiceImpl implements RepairCategoryService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RepairCategoryServiceImpl.class);
    RepairCategoryRepository repairCategoryRepository;

    ModelMapper modelMapper;

    public RepairCategoryServiceImpl(RepairCategoryRepository repairCategoryRepository, ModelMapper modelMapper) {
        this.repairCategoryRepository = repairCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<RepairCategoryDTO> findAll(Pageable pageable) {
        return repairCategoryRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public List<RepairCategoryDTO> findAll() {
        return repairCategoryRepository.findAll(Sort.by("keyName"))
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepairCategoryDTO findById(Long id) {
        return constructDTO(repairCategoryRepository.findById(id).orElseThrow());
    }

    @Override
    public RepairCategoryDTO findByKeyName(String keyName) {
        return constructDTO(repairCategoryRepository.findByKeyName(keyName).orElseThrow());
    }

    public RepairCategoryDTO constructDTO(RepairCategory repairCategory) {
        return modelMapper.map(repairCategory,RepairCategoryDTO.class);
    }
}
