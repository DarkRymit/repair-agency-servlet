package com.epam.finalproject.util;

import com.epam.finalproject.config.SearchParameters;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.UserSearch;
import com.epam.finalproject.request.search.ReceiptSearchRequest;
import com.epam.finalproject.request.search.UserSearchRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SearchRequestResolverTest {



    final static SearchRequestResolver searchRequestResolver;

    final static SearchParameters parameters;

    static {

        HashMap<String, Sort> receiptSortMap = new HashMap<>();

        receiptSortMap.put("create-time", Sort.by("creationDate"));
        receiptSortMap.put("status", Sort.by("receiptStatus"));
        receiptSortMap.put("",  Sort.by("creationDate"));

        Map<String, Sort> receiptSort = Collections.unmodifiableMap(receiptSortMap);

        HashMap<String, ReceiptStatusEnum> receiptStatusMap = new HashMap<>();

        receiptStatusMap.put("done", ReceiptStatusEnum.DONE);
        receiptStatusMap.put("paid", ReceiptStatusEnum.PAID);
        receiptStatusMap.put("cancel", ReceiptStatusEnum.CANCELED);

        Map<String, ReceiptStatusEnum> receiptStatus = Collections.unmodifiableMap(receiptStatusMap);

        HashMap<String, Sort> userSortMap = new HashMap<>();

        userSortMap.put("username", Sort.by("username"));
        userSortMap.put("name", Sort.by("firstName").and(Sort.by("lastName")));
        userSortMap.put("",  Sort.by("username"));

        Map<String, Sort> userSort =  Collections.unmodifiableMap(userSortMap);

        parameters = new SearchParameters(receiptSort,userSort,receiptStatus);
        searchRequestResolver=new SearchRequestResolver(parameters);
    }


    @Test
    void resolveUserRequest() {
        UserSearchRequest request = new UserSearchRequest("", Set.of(),"user",0,10);

        UserSearch userSearch = searchRequestResolver.resolve(request);

        assertEquals("user",userSearch.getUsername());
        PageRequest pageRequest = userSearch.getPageRequest();
        assertEquals(Sort.by("username"),pageRequest.getSort());
        assertEquals(0,pageRequest.getPageNumber());
        assertEquals(10,pageRequest.getPageSize());
    }

    @Test
    void resolveReceiptRequest() {
        ReceiptSearchRequest request = new ReceiptSearchRequest("",Set.of("paid","done"),"","mast",2,15);

        ReceiptSearch receiptSearch = searchRequestResolver.resolve(request);

        assertEquals("mast",receiptSearch.getMasterUsername());
        assertEquals("",receiptSearch.getCustomerUsername());
        assertThat(receiptSearch.getReceiptStatuses()).containsExactlyInAnyOrder(ReceiptStatusEnum.DONE,ReceiptStatusEnum.PAID);
        PageRequest pageRequest = receiptSearch.getPageRequest();
        assertEquals(Sort.by("creationDate"),pageRequest.getSort());
        assertEquals(2,pageRequest.getPageNumber());
        assertEquals(15,pageRequest.getPageSize());
    }
}