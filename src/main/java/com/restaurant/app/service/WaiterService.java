package com.restaurant.app.service;

import com.restaurant.app.service.dto.WaiterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.app.domain.Waiter}.
 */
public interface WaiterService {
    /**
     * Save a waiter.
     *
     * @param waiterDTO the entity to save.
     * @return the persisted entity.
     */
    WaiterDTO save(WaiterDTO waiterDTO);

    /**
     * Partially updates a waiter.
     *
     * @param waiterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WaiterDTO> partialUpdate(WaiterDTO waiterDTO);

    /**
     * Get all the waiters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WaiterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" waiter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WaiterDTO> findOne(Long id);

    /**
     * Delete the "id" waiter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
