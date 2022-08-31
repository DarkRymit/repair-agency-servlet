package com.epam.finalproject.framework.data.repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Crud repository.
 * Basic interface to perform crud methods contract to implementations
 */
public interface CrudRepository<T, ID> extends Repository<T, ID> {
    /**
     * Save t.
     * Saves field of entity for more read impl javadoc
     *
     * @param entity the entity
     * @return the t
     */
    T save(T entity);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional of entity or {@link Optional#empty()}
     */
    Optional<T> findById(ID id);

    /**
     * Exists by id boolean.
     *
     * @param id the id
     * @return the true if exist or false otherwise
     */
    boolean existsById(ID id);

    /**
     * Find all list.
     *
     * @return the list of entities or empty list otherwise
     */
    List<T> findAll();

    /**
     * Count long.
     *
     * @return the number of entity in repository
     */
    long count();

    /**
     * Delete by id.
     *
     * @param id the id of entity to delete
     */
    void deleteById(ID id);

    /**
     * Delete.
     *
     * @param entity the entity with not null id
     */
    void delete(T entity);

}
