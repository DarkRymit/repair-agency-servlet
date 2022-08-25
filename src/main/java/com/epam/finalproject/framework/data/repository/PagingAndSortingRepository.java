package com.epam.finalproject.framework.data.repository;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.Sort;

import java.util.List;

public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

}
