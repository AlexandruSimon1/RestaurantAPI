package com.restaurant.app.service;

import com.restaurant.app.domain.*; // for static metamodels
import com.restaurant.app.domain.Administrator;
import com.restaurant.app.repository.AdministratorRepository;
import com.restaurant.app.service.criteria.AdministratorCriteria;
import com.restaurant.app.service.dto.AdministratorDTO;
import com.restaurant.app.service.mapper.AdministratorMapper;
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
 * Service for executing complex queries for {@link Administrator} entities in the database.
 * The main input is a {@link AdministratorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdministratorDTO} or a {@link Page} of {@link AdministratorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdministratorQueryService extends QueryService<Administrator> {

    private final Logger log = LoggerFactory.getLogger(AdministratorQueryService.class);

    private final AdministratorRepository administratorRepository;

    private final AdministratorMapper administratorMapper;

    public AdministratorQueryService(AdministratorRepository administratorRepository, AdministratorMapper administratorMapper) {
        this.administratorRepository = administratorRepository;
        this.administratorMapper = administratorMapper;
    }

    /**
     * Return a {@link List} of {@link AdministratorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdministratorDTO> findByCriteria(AdministratorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Administrator> specification = createSpecification(criteria);
        return administratorMapper.toDto(administratorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdministratorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdministratorDTO> findByCriteria(AdministratorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Administrator> specification = createSpecification(criteria);
        return administratorRepository.findAll(specification, page).map(administratorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdministratorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Administrator> specification = createSpecification(criteria);
        return administratorRepository.count(specification);
    }

    /**
     * Function to convert {@link AdministratorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Administrator> createSpecification(AdministratorCriteria criteria) {
        Specification<Administrator> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Administrator_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Administrator_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Administrator_.lastName));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Administrator_.dateOfBirth));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Administrator_.address));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhoneNumber(), Administrator_.phoneNumber));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Administrator_.email));
            }
        }
        return specification;
    }
}
