package com.restaurant.app.service.impl;

import com.restaurant.app.domain.CheckOut;
import com.restaurant.app.repository.CheckOutRepository;
import com.restaurant.app.service.CheckOutService;
import com.restaurant.app.service.dto.CheckOutDTO;
import com.restaurant.app.service.mapper.CheckOutMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CheckOut}.
 */
@Service
@Transactional
public class CheckOutServiceImpl implements CheckOutService {

    private final Logger log = LoggerFactory.getLogger(CheckOutServiceImpl.class);

    private final CheckOutRepository checkOutRepository;

    private final CheckOutMapper checkOutMapper;

    public CheckOutServiceImpl(CheckOutRepository checkOutRepository, CheckOutMapper checkOutMapper) {
        this.checkOutRepository = checkOutRepository;
        this.checkOutMapper = checkOutMapper;
    }

    @Override
    public CheckOutDTO save(CheckOutDTO checkOutDTO) {
        log.debug("Request to save CheckOut : {}", checkOutDTO);
        CheckOut checkOut = checkOutMapper.toEntity(checkOutDTO);
        checkOut = checkOutRepository.save(checkOut);
        return checkOutMapper.toDto(checkOut);
    }

    @Override
    public Optional<CheckOutDTO> partialUpdate(CheckOutDTO checkOutDTO) {
        log.debug("Request to partially update CheckOut : {}", checkOutDTO);

        return checkOutRepository
            .findById(checkOutDTO.getId())
            .map(existingCheckOut -> {
                checkOutMapper.partialUpdate(existingCheckOut, checkOutDTO);

                return existingCheckOut;
            })
            .map(checkOutRepository::save)
            .map(checkOutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckOutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckOuts");
        return checkOutRepository.findAll(pageable).map(checkOutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckOutDTO> findOne(Long id) {
        log.debug("Request to get CheckOut : {}", id);
        return checkOutRepository.findById(id).map(checkOutMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckOut : {}", id);
        checkOutRepository.deleteById(id);
    }
}
