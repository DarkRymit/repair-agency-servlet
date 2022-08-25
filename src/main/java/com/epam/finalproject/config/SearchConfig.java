package com.epam.finalproject.config;

import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.data.Sort;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SearchConfig {


    @Bean(clazz = SearchParameters.class)
    SearchParameters searchParameters(){
        return new SearchParameters(receiptSort(),userSort(),receiptStatus());
    }


    Map<String, Sort> receiptSort() {
        HashMap<String, Sort> hashMap = new HashMap<>();

        Sort sortByTime = Sort.by("creationDate");

        hashMap.put("price", Sort.by("totalPrice").and(Sort.by("priceCurrency").and(sortByTime)));
        hashMap.put("create-time", sortByTime);
        hashMap.put("status", Sort.by("status").and(sortByTime));
        hashMap.put("", sortByTime);

        return Map.copyOf(hashMap);
    }

    Map<String, ReceiptStatusEnum> receiptStatus() {
        HashMap<String, ReceiptStatusEnum> hashMap = new HashMap<>();

        hashMap.put("created", ReceiptStatusEnum.CREATED);
        hashMap.put("done", ReceiptStatusEnum.DONE);
        hashMap.put("paid", ReceiptStatusEnum.PAID);
        hashMap.put("cancel", ReceiptStatusEnum.CANCELED);
        hashMap.put("work", ReceiptStatusEnum.IN_WORK);
        hashMap.put("wait", ReceiptStatusEnum.WAIT_FOR_PAYMENT);

        return Map.copyOf(hashMap);
    }


    Map<String, Sort> userSort() {
        HashMap<String, Sort> hashMap = new HashMap<>();

        Sort sortByUsername = Sort.by("username");
        hashMap.put("username", sortByUsername);
        hashMap.put("name", Sort.by("firstName").and(Sort.by("lastName").and(sortByUsername)));
        hashMap.put("", sortByUsername);

        return Map.copyOf(hashMap);
    }

}
