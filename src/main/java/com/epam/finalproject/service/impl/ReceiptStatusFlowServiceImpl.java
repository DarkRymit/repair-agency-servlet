package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.repository.ReceiptStatusFlowRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.service.ReceiptStatusFlowService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptStatusFlowServiceImpl implements ReceiptStatusFlowService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReceiptStatusFlowServiceImpl.class);
    ReceiptStatusFlowRepository receiptStatusFlowRepository;

    UserRepository userRepository;

    ModelMapper modelMapper;

    public ReceiptStatusFlowServiceImpl(ReceiptStatusFlowRepository receiptStatusFlowRepository,
            UserRepository userRepository, ModelMapper modelMapper) {
        this.receiptStatusFlowRepository = receiptStatusFlowRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ReceiptStatusFlowDTO> findAll() {
        return receiptStatusFlowRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::constructDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReceiptStatusFlowDTO> findAll(Pageable pageable) {
        return receiptStatusFlowRepository.findAll(pageable).map(this::constructDTO);
    }

    @Override
    public ReceiptStatusFlowDTO constructDTO(ReceiptStatusFlow flow) {
        ReceiptStatusFlowDTO result = new ReceiptStatusFlowDTO();
        modelMapper.map(flow, result);
        log.info(result.toString());
        return result;
    }

    @Override
    public ReceiptStatusFlowDTO findById(Long id) {
        return constructDTO(receiptStatusFlowRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ReceiptStatusFlowDTO> listAllAvailableForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<ReceiptStatusFlow> flows = receiptStatusFlowRepository.findDistinctByRoleIn(user.getRoles());
        return flows.stream().map(this::constructDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReceiptStatusFlowDTO> listAllAvailableForUser(Long fromId,String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<ReceiptStatusFlow> flows = receiptStatusFlowRepository.findDistinctByFromStatus_IdAndRoleIn(fromId,user.getRoles());
        return flows.stream().map(this::constructDTO).collect(Collectors.toList());
    }
}
