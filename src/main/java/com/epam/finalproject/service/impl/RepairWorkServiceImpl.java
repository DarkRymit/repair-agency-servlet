package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.RepairWork;
import com.epam.finalproject.repository.RepairWorkRepository;
import com.epam.finalproject.service.RepairWorkService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairWorkServiceImpl implements RepairWorkService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RepairWorkServiceImpl.class);
    RepairWorkRepository repairWorkRepository;

    ModelMapper modelMapper;

    public RepairWorkServiceImpl(RepairWorkRepository repairWorkRepository, ModelMapper modelMapper) {
        this.repairWorkRepository = repairWorkRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<RepairWorkDTO> findAll(Pageable pageable) {
        return repairWorkRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public RepairWorkDTO findById(Long id) {
        return constructDTO(repairWorkRepository.findById(id).orElseThrow());
    }

    @Override
    public List<RepairWorkDTO> findByKey(String key) {
        return repairWorkRepository.findByKeyName(key).stream().map(this::constructDTO).collect(Collectors.toList());
    }

    @Override
    public List<RepairWorkDTO> findByCategoryKey(String key) {
        return repairWorkRepository.findByCategoryKeyName(key)
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepairWorkDTO findByKeyAndCategoryKey(String workKey, String categoryKey) {
        return constructDTO(repairWorkRepository.findByKeyNameAndCategory_KeyName(workKey, categoryKey).orElseThrow());
    }

    public RepairWorkDTO constructDTO(RepairWork repairWork) {
        return modelMapper.map(repairWork, RepairWorkDTO.class);
    }
}
