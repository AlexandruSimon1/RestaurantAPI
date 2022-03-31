package com.restaurant.app.service.impl;

import com.restaurant.app.domain.Waiter;
import com.restaurant.app.repository.WaiterRepository;
import com.restaurant.app.service.WaiterService;
import com.restaurant.app.service.dto.WaiterDTO;
import com.restaurant.app.service.mapper.WaiterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Waiter}.
 */
@Service
@Transactional
public class WaiterServiceImpl implements WaiterService {

    private final Logger log = LoggerFactory.getLogger(WaiterServiceImpl.class);

    private final WaiterRepository waiterRepository;

    private final WaiterMapper waiterMapper;

    public WaiterServiceImpl(WaiterRepository waiterRepository, WaiterMapper waiterMapper) {
        this.waiterRepository = waiterRepository;
        this.waiterMapper = waiterMapper;
    }

    @Override
    public WaiterDTO save(WaiterDTO waiterDTO) {
        log.debug("Request to save Waiter : {}", waiterDTO);
        Waiter waiter = waiterMapper.toEntity(waiterDTO);
        waiter = waiterRepository.save(waiter);
        return waiterMapper.toDto(waiter);
    }

    @Override
    public Optional<WaiterDTO> partialUpdate(WaiterDTO waiterDTO) {
        log.debug("Request to partially update Waiter : {}", waiterDTO);

        return waiterRepository
            .findById(waiterDTO.getId())
            .map(existingWaiter -> {
                waiterMapper.partialUpdate(existingWaiter, waiterDTO);

                return existingWaiter;
            })
            .map(waiterRepository::save)
            .map(waiterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WaiterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Waiters");
        return waiterRepository.findAll(pageable).map(waiterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WaiterDTO> findOne(Long id) {
        log.debug("Request to get Waiter : {}", id);
        return waiterRepository.findById(id).map(waiterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Waiter : {}", id);
        waiterRepository.deleteById(id);
    }
}
