package com.epam.finalproject.framework.data.repository;

import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.Pageable;
import com.epam.finalproject.framework.data.Sort;

import java.util.List;

/**
 * The interface Paging and sorting repository.
 * Basic interface to perform paging or sorting methods contract to implementations
 *
 * @see Page Page
 * @see Sort Sort
 */
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

    /**
     * Find all list.
     *
     * @param sort the sort
     * @return the list with entities sorted by  {@link Sort} param or empty list otherwise
     */
    List<T> findAll(Sort sort);

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page with entities sorted by  {@link Sort} param and sized with offset by  {@link Pageable} or page with empty list otherwise
     */
    Page<T> findAll(Pageable pageable);

}
