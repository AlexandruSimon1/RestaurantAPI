package com.restaurant.app.web.rest;

import com.restaurant.app.repository.WaiterRepository;
import com.restaurant.app.service.WaiterQueryService;
import com.restaurant.app.service.WaiterService;
import com.restaurant.app.service.criteria.WaiterCriteria;
import com.restaurant.app.service.dto.WaiterDTO;
import com.restaurant.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.restaurant.app.domain.Waiter}.
 */
@RestController
@RequestMapping("/api")
public class WaiterResource {

    private final Logger log = LoggerFactory.getLogger(WaiterResource.class);

    private static final String ENTITY_NAME = "waiter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaiterService waiterService;

    private final WaiterRepository waiterRepository;

    private final WaiterQueryService waiterQueryService;

    public WaiterResource(WaiterService waiterService, WaiterRepository waiterRepository, WaiterQueryService waiterQueryService) {
        this.waiterService = waiterService;
        this.waiterRepository = waiterRepository;
        this.waiterQueryService = waiterQueryService;
    }

    /**
     * {@code POST  /waiters} : Create a new waiter.
     *
     * @param waiterDTO the waiterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waiterDTO, or with status {@code 400 (Bad Request)} if the waiter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/waiters")
    public ResponseEntity<WaiterDTO> createWaiter(@RequestBody WaiterDTO waiterDTO) throws URISyntaxException {
        log.debug("REST request to save Waiter : {}", waiterDTO);
        if (waiterDTO.getId() != null) {
            throw new BadRequestAlertException("A new waiter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WaiterDTO result = waiterService.save(waiterDTO);
        return ResponseEntity
            .created(new URI("/api/waiters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /waiters/:id} : Updates an existing waiter.
     *
     * @param id the id of the waiterDTO to save.
     * @param waiterDTO the waiterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waiterDTO,
     * or with status {@code 400 (Bad Request)} if the waiterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the waiterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/waiters/{id}")
    public ResponseEntity<WaiterDTO> updateWaiter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WaiterDTO waiterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Waiter : {}, {}", id, waiterDTO);
        if (waiterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waiterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waiterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WaiterDTO result = waiterService.save(waiterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, waiterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /waiters/:id} : Partial updates given fields of an existing waiter, field will ignore if it is null
     *
     * @param id the id of the waiterDTO to save.
     * @param waiterDTO the waiterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waiterDTO,
     * or with status {@code 400 (Bad Request)} if the waiterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the waiterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the waiterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/waiters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WaiterDTO> partialUpdateWaiter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WaiterDTO waiterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Waiter partially : {}, {}", id, waiterDTO);
        if (waiterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waiterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waiterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WaiterDTO> result = waiterService.partialUpdate(waiterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, waiterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /waiters} : get all the waiters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waiters in body.
     */
    @GetMapping("/waiters")
    public ResponseEntity<List<WaiterDTO>> getAllWaiters(
        WaiterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Waiters by criteria: {}", criteria);
        Page<WaiterDTO> page = waiterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /waiters/count} : count all the waiters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/waiters/count")
    public ResponseEntity<Long> countWaiters(WaiterCriteria criteria) {
        log.debug("REST request to count Waiters by criteria: {}", criteria);
        return ResponseEntity.ok().body(waiterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /waiters/:id} : get the "id" waiter.
     *
     * @param id the id of the waiterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waiterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/waiters/{id}")
    public ResponseEntity<WaiterDTO> getWaiter(@PathVariable Long id) {
        log.debug("REST request to get Waiter : {}", id);
        Optional<WaiterDTO> waiterDTO = waiterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(waiterDTO);
    }

    /**
     * {@code DELETE  /waiters/:id} : delete the "id" waiter.
     *
     * @param id the id of the waiterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/waiters/{id}")
    public ResponseEntity<Void> deleteWaiter(@PathVariable Long id) {
        log.debug("REST request to delete Waiter : {}", id);
        waiterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
