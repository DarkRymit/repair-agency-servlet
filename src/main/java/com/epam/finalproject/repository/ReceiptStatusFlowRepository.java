package com.epam.finalproject.repository;

import com.epam.finalproject.framework.data.repository.PagingAndSortingRepository;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.ReceiptStatusFlow;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ReceiptStatusFlowRepository extends PagingAndSortingRepository<ReceiptStatusFlow, Long> {

    List<ReceiptStatusFlow> findDistinctByRoleIn(Set<Role> role);
    List<ReceiptStatusFlow> findDistinctByFromStatus_IdAndRoleIn(Long fromId,Set<Role> roles);

    boolean existsByFromStatusAndToStatusAndRoleIn(ReceiptStatus fromStatus, ReceiptStatus toStatus, Collection<Role> roles);
}
