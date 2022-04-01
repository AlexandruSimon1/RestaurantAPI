package com.restaurant.app.web.rest;

import com.restaurant.app.repository.TableRepository;
import com.restaurant.app.service.TableQueryService;
import com.restaurant.app.service.TableService;
import com.restaurant.app.service.criteria.TableCriteria;
import com.restaurant.app.service.dto.TableDTO;
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
 * REST controller for managing {@link com.restaurant.app.domain.Table}.
 */
@RestController
@RequestMapping("/tables")
public class TableResource {

    private final Logger log = LoggerFactory.getLogger(TableResource.class);

    private static final String ENTITY_NAME = "table";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TableService tableService;

    private final TableRepository tableRepository;

    private final TableQueryService tableQueryService;

    public TableResource(TableService tableService, TableRepository tableRepository, TableQueryService tableQueryService) {
        this.tableService = tableService;
        this.tableRepository = tableRepository;
        this.tableQueryService = tableQueryService;
    }

    /**
     * {@code POST  /tables} : Create a new table.
     *
     * @param tableDTO the tableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tableDTO, or with status {@code 400 (Bad Request)} if the table has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<TableDTO> createTable(@RequestBody TableDTO tableDTO) throws URISyntaxException {
        log.debug("REST request to save Table : {}", tableDTO);
        if (tableDTO.getId() != null) {
            throw new BadRequestAlertException("A new table cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TableDTO result = tableService.save(tableDTO);
        return ResponseEntity
            .created(new URI("/api/tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tables/:id} : Updates an existing table.
     *
     * @param id the id of the tableDTO to save.
     * @param tableDTO the tableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tableDTO,
     * or with status {@code 400 (Bad Request)} if the tableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TableDTO> updateTable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TableDTO tableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Table : {}, {}", id, tableDTO);
        if (tableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TableDTO result = tableService.save(tableDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tables/:id} : Partial updates given fields of an existing table, field will ignore if it is null
     *
     * @param id the id of the tableDTO to save.
     * @param tableDTO the tableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tableDTO,
     * or with status {@code 400 (Bad Request)} if the tableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TableDTO> partialUpdateTable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TableDTO tableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Table partially : {}, {}", id, tableDTO);
        if (tableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TableDTO> result = tableService.partialUpdate(tableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tables} : get all the tables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tables in body.
     */
    @GetMapping
    public ResponseEntity<List<TableDTO>> getAllTables(
        TableCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Tables by criteria: {}", criteria);
        Page<TableDTO> page = tableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tables/count} : count all the tables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTables(TableCriteria criteria) {
        log.debug("REST request to count Tables by criteria: {}", criteria);
        return ResponseEntity.ok().body(tableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tables/:id} : get the "id" table.
     *
     * @param id the id of the tableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TableDTO> getTable(@PathVariable Long id) {
        log.debug("REST request to get Table : {}", id);
        Optional<TableDTO> tableDTO = tableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tableDTO);
    }

    /**
     * {@code DELETE  /tables/:id} : delete the "id" table.
     *
     * @param id the id of the tableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        log.debug("REST request to delete Table : {}", id);
        tableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
