package com.restaurant.app.service;

import com.restaurant.app.domain.*; // for static metamodels
import com.restaurant.app.domain.Table;
import com.restaurant.app.repository.TableRepository;
import com.restaurant.app.service.criteria.TableCriteria;
import com.restaurant.app.service.dto.TableDTO;
import com.restaurant.app.service.mapper.TableMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Table} entities in the database.
 * The main input is a {@link TableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TableDTO} or a {@link Page} of {@link TableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TableQueryService extends QueryService<Table> {

    private final Logger log = LoggerFactory.getLogger(TableQueryService.class);

    private final TableRepository tableRepository;

    private final TableMapper tableMapper;

    public TableQueryService(TableRepository tableRepository, TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    /**
     * Return a {@link List} of {@link TableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TableDTO> findByCriteria(TableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Table> specification = createSpecification(criteria);
        return tableMapper.toDto(tableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TableDTO> findByCriteria(TableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Table> specification = createSpecification(criteria);
        return tableRepository.findAll(specification, page).map(tableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Table> specification = createSpecification(criteria);
        return tableRepository.count(specification);
    }

    /**
     * Function to convert {@link TableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Table> createSpecification(TableCriteria criteria) {
        Specification<Table> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Table_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Table_.number));
            }
            if (criteria.getOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOrderId(), root -> root.join(Table_.order, JoinType.LEFT).get(Order_.id))
                    );
            }
        }
        return specification;
    }
}
