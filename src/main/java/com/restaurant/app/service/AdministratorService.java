package com.restaurant.app.service;

import com.restaurant.app.service.dto.AdministratorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.app.domain.Administrator}.
 */
public interface AdministratorService {
    /**
     * Save a administrator.
     *
     * @param administratorDTO the entity to save.
     * @return the persisted entity.
     */
    AdministratorDTO save(AdministratorDTO administratorDTO);

    /**
     * Partially updates a administrator.
     *
     * @param administratorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdministratorDTO> partialUpdate(AdministratorDTO administratorDTO);

    /**
     * Get all the administrators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdministratorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" administrator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdministratorDTO> findOne(Long id);

    /**
     * Delete the "id" administrator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
