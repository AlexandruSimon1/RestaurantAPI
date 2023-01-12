package com.application.service;

import com.application.dto.TableDTO;

import java.util.List;

public interface TableService {
    List<TableDTO> getAllTable();

    TableDTO getTableById(int tableId);

    TableDTO deleteTableById(int tableId);

    TableDTO createTable(TableDTO tableDTO);

    TableDTO update(TableDTO tableDTO, int tableId);
}
