package com.restaurant.app.service.impl;

import com.restaurant.app.domain.Table;
import com.restaurant.app.repository.TableRepository;
import com.restaurant.app.service.TableService;
import com.restaurant.app.service.dto.TableDTO;
import com.restaurant.app.service.mapper.TableMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Table}.
 */
@Service
@Transactional
public class TableServiceImpl implements TableService {

    private final Logger log = LoggerFactory.getLogger(TableServiceImpl.class);

    private final TableRepository tableRepository;

    private final TableMapper tableMapper;

    public TableServiceImpl(TableRepository tableRepository, TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    @Override
    public TableDTO save(TableDTO tableDTO) {
        log.debug("Request to save Table : {}", tableDTO);
        Table table = tableMapper.toEntity(tableDTO);
        table = tableRepository.save(table);
        return tableMapper.toDto(table);
    }

    @Override
    public Optional<TableDTO> partialUpdate(TableDTO tableDTO) {
        log.debug("Request to partially update Table : {}", tableDTO);

        return tableRepository
            .findById(tableDTO.getId())
            .map(existingTable -> {
                tableMapper.partialUpdate(existingTable, tableDTO);

                return existingTable;
            })
            .map(tableRepository::save)
            .map(tableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tables");
        return tableRepository.findAll(pageable).map(tableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TableDTO> findOne(Long id) {
        log.debug("Request to get Table : {}", id);
        return tableRepository.findById(id).map(tableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Table : {}", id);
        tableRepository.deleteById(id);
    }
}
