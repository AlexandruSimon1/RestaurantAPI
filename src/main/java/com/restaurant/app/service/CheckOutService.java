package com.restaurant.app.service;

import com.restaurant.app.service.dto.CheckOutDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.app.domain.CheckOut}.
 */
public interface CheckOutService {
    /**
     * Save a checkOut.
     *
     * @param checkOutDTO the entity to save.
     * @return the persisted entity.
     */
    CheckOutDTO save(CheckOutDTO checkOutDTO);

    /**
     * Partially updates a checkOut.
     *
     * @param checkOutDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CheckOutDTO> partialUpdate(CheckOutDTO checkOutDTO);

    /**
     * Get all the checkOuts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckOutDTO> findAll(Pageable pageable);

    /**
     * Get the "id" checkOut.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckOutDTO> findOne(Long id);

    /**
     * Delete the "id" checkOut.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
