package com.restaurant.app.service;

import com.restaurant.app.service.dto.TableDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.app.domain.Table}.
 */
public interface TableService {
    /**
     * Save a table.
     *
     * @param tableDTO the entity to save.
     * @return the persisted entity.
     */
    TableDTO save(TableDTO tableDTO);

    /**
     * Partially updates a table.
     *
     * @param tableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TableDTO> partialUpdate(TableDTO tableDTO);

    /**
     * Get all the tables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TableDTO> findAll(Pageable pageable);

    /**
     * Get the "id" table.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TableDTO> findOne(Long id);

    /**
     * Delete the "id" table.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
