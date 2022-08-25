package com.epam.finalproject.model.search;

import com.epam.finalproject.framework.data.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class MasterSearch {
    PageRequest pageRequest;
    String username;
}
