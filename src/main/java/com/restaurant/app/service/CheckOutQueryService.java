package com.restaurant.app.service;

import com.restaurant.app.domain.*; // for static metamodels
import com.restaurant.app.domain.CheckOut;
import com.restaurant.app.repository.CheckOutRepository;
import com.restaurant.app.service.criteria.CheckOutCriteria;
import com.restaurant.app.service.dto.CheckOutDTO;
import com.restaurant.app.service.mapper.CheckOutMapper;
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
 * Service for executing complex queries for {@link CheckOut} entities in the database.
 * The main input is a {@link CheckOutCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CheckOutDTO} or a {@link Page} of {@link CheckOutDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CheckOutQueryService extends QueryService<CheckOut> {

    private final Logger log = LoggerFactory.getLogger(CheckOutQueryService.class);

    private final CheckOutRepository checkOutRepository;

    private final CheckOutMapper checkOutMapper;

    public CheckOutQueryService(CheckOutRepository checkOutRepository, CheckOutMapper checkOutMapper) {
        this.checkOutRepository = checkOutRepository;
        this.checkOutMapper = checkOutMapper;
    }

    /**
     * Return a {@link List} of {@link CheckOutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CheckOutDTO> findByCriteria(CheckOutCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CheckOut> specification = createSpecification(criteria);
        return checkOutMapper.toDto(checkOutRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CheckOutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckOutDTO> findByCriteria(CheckOutCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CheckOut> specification = createSpecification(criteria);
        return checkOutRepository.findAll(specification, page).map(checkOutMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CheckOutCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CheckOut> specification = createSpecification(criteria);
        return checkOutRepository.count(specification);
    }

    /**
     * Function to convert {@link CheckOutCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CheckOut> createSpecification(CheckOutCriteria criteria) {
        Specification<CheckOut> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CheckOut_.id));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentType(), CheckOut_.paymentType));
            }
            if (criteria.getOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOrderId(), root -> root.join(CheckOut_.order, JoinType.LEFT).get(Order_.id))
                    );
            }
        }
        return specification;
    }
}
