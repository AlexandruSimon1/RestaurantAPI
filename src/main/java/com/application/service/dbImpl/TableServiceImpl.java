package com.application.service.dbImpl;

import com.application.dto.TableDTO;
import com.application.exceptions.ApplicationException;
import com.application.exceptions.ExceptionType;
import com.application.mapper.NotificatorMappingContext;
import com.application.mapper.TableMapper;
import com.application.model.Table;
import com.application.repository.TableRepository;
import com.application.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class TableServiceImpl implements TableService {
    @Autowired
    private final TableRepository tableRepository;

    @Override
    public List<TableDTO> getAllTable() {
        return tableRepository.findAll().stream().
                map(table -> TableMapper.INSTANCE.toTableDto(table, new NotificatorMappingContext())).
                collect(Collectors.toList());
    }

    @Override
    public TableDTO getTableById(int tableId) {
        final Table getTable = tableRepository.findById(tableId).
                orElseThrow(() -> new ApplicationException(ExceptionType.TABLE_NOT_FOUND));
        return TableMapper.INSTANCE.toTableDto(getTable, new NotificatorMappingContext());
    }

    @Override
    public TableDTO deleteTableById(int tableId) {
        final Table deleteTable = tableRepository.findById(tableId).
                orElseThrow(() -> new ApplicationException(ExceptionType.TABLE_NOT_FOUND));
        return TableMapper.INSTANCE.toTableDto(deleteTable, new NotificatorMappingContext());
    }

    @Override
    public TableDTO createTable(TableDTO tableDTO) {
        final Table createTable = TableMapper.INSTANCE.fromTableDto(tableDTO, new NotificatorMappingContext());
        final Table saveTable = tableRepository.save(createTable);
        return TableMapper.INSTANCE.toTableDto(saveTable, new NotificatorMappingContext());
    }

    @Override
    public TableDTO update(TableDTO tableDTO, int tableId) {
        final Table updateTable = tableRepository.findById(tableId).
                orElseThrow(() -> new ApplicationException(ExceptionType.TABLE_NOT_FOUND));
        updateTable.setNumber(tableDTO.getNumber());
        tableRepository.save(updateTable);
        return TableMapper.INSTANCE.toTableDto(updateTable, new NotificatorMappingContext());
    }
}
