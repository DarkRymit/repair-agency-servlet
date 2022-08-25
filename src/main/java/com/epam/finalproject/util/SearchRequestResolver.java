package com.epam.finalproject.util;

import com.epam.finalproject.config.SearchParameters;
import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.*;
import com.epam.finalproject.request.search.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SearchRequestResolver {

    Map<String, Sort> receiptSort;

    Map<String, Sort> userSort;

    Map<String, ReceiptStatusEnum> receiptStatus;

    SearchParameters searchParameters;

    public SearchRequestResolver(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
        this.receiptSort = searchParameters.getReceiptSort();
        this.userSort = searchParameters.getUserSort();
        this.receiptStatus = searchParameters.getReceiptStatus();
    }

    public ReceiptSearch resolve(ReceiptSearchRequest receiptSearchRequest) {

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

        Sort sort = receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        Set<ReceiptStatusEnum> receiptStatuses = Optional.ofNullable(receiptSearchRequest.getStatus())
                .orElse(Set.of())
                .stream()
                .map(e -> Optional.ofNullable(receiptStatus.get(e)).orElseThrow())
                .collect(Collectors.toSet());

        return ReceiptSearch.builder()
                .receiptStatuses(receiptStatuses)
                .customerUsername(receiptSearchRequest.getCustomer())
                .masterUsername(receiptSearchRequest.getMaster())
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 5), sort))
                .build();
    }
    public ReceiptWithCustomerSearch resolve(ReceiptWithCustomerSearchRequest receiptSearchRequest, User customer) {

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

        Sort sort = receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        Set<ReceiptStatusEnum> receiptStatuses = Optional.ofNullable(receiptSearchRequest.getStatus())
                .orElse(Set.of())
                .stream()
                .map(e -> Optional.ofNullable(receiptStatus.get(e)).orElseThrow())
                .collect(Collectors.toSet());

        return ReceiptWithCustomerSearch.builder()
                .receiptStatuses(receiptStatuses)
                .customer(customer)
                .masterUsername(receiptSearchRequest.getMaster())
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 5), sort))
                .build();
    }
    public ReceiptWithMasterSearch resolve(ReceiptWithMasterSearchRequest receiptSearchRequest, User master) {

        String sortValue = getValueOrDefault(receiptSearchRequest.getSort(),"");

        Sort sort = receiptSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        Set<ReceiptStatusEnum> receiptStatuses = Optional.ofNullable(receiptSearchRequest.getStatus())
                .orElse(Set.of())
                .stream()
                .map(e -> Optional.ofNullable(receiptStatus.get(e)).orElseThrow())
                .collect(Collectors.toSet());

        return ReceiptWithMasterSearch.builder()
                .receiptStatuses(receiptStatuses)
                .customerUsername(receiptSearchRequest.getCustomer())
                .master(master)
                .pageRequest(PageRequest.of(getValueOrZero(receiptSearchRequest.getPage()), getValueOrDefault(receiptSearchRequest.getCount(), 5), sort))
                .build();
    }

    public UserSearch resolve(UserSearchRequest userSearchRequest) {

        String sortValue = Optional.ofNullable(userSearchRequest.getSort()).orElse("");

        Sort sort = userSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        return UserSearch.builder()
                .username(userSearchRequest.getUsername())
                .pageRequest(PageRequest.of(getValueOrZero(userSearchRequest.getPage()), getValueOrDefault(userSearchRequest.getCount(), 5), sort))
                .build();
    }

    public MasterSearch resolve(MasterSearchRequest masterSearchRequest) {

        String sortValue = getValueOrDefault(masterSearchRequest.getSort(),"");

        Sort sort = userSort.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(sortValue))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        return MasterSearch.builder()
                .username(masterSearchRequest.getUsername())
                .pageRequest(PageRequest.of(getValueOrZero(masterSearchRequest.getPage()), getValueOrDefault(masterSearchRequest.getCount(), 5), sort))
                .build();
    }

    private Integer getValueOrZero(Integer value) {
        return getValueOrDefault(value, 0);
    }

    private <T> T getValueOrDefault(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
}
