package com.restaurant.app.service;

import com.restaurant.app.domain.*; // for static metamodels
import com.restaurant.app.domain.Waiter;
import com.restaurant.app.repository.WaiterRepository;
import com.restaurant.app.service.criteria.WaiterCriteria;
import com.restaurant.app.service.dto.WaiterDTO;
import com.restaurant.app.service.mapper.WaiterMapper;
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
 * Service for executing complex queries for {@link Waiter} entities in the database.
 * The main input is a {@link WaiterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WaiterDTO} or a {@link Page} of {@link WaiterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WaiterQueryService extends QueryService<Waiter> {

    private final Logger log = LoggerFactory.getLogger(WaiterQueryService.class);

    private final WaiterRepository waiterRepository;

    private final WaiterMapper waiterMapper;

    public WaiterQueryService(WaiterRepository waiterRepository, WaiterMapper waiterMapper) {
        this.waiterRepository = waiterRepository;
        this.waiterMapper = waiterMapper;
    }

    /**
     * Return a {@link List} of {@link WaiterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WaiterDTO> findByCriteria(WaiterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Waiter> specification = createSpecification(criteria);
        return waiterMapper.toDto(waiterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WaiterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WaiterDTO> findByCriteria(WaiterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Waiter> specification = createSpecification(criteria);
        return waiterRepository.findAll(specification, page).map(waiterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WaiterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Waiter> specification = createSpecification(criteria);
        return waiterRepository.count(specification);
    }

    /**
     * Function to convert {@link WaiterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Waiter> createSpecification(WaiterCriteria criteria) {
        Specification<Waiter> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Waiter_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Waiter_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Waiter_.lastName));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Waiter_.dateOfBirth));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Waiter_.address));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhoneNumber(), Waiter_.phoneNumber));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Waiter_.email));
            }
        }
        return specification;
    }
}
