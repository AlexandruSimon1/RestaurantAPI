package com.application.service.dbImpl;

import com.application.dto.TableDTO;
import com.application.model.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.application.repository.TableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {
    private static final int ID_VALUE = 1;
    private Table table;
    private TableServiceImpl tableService;

    @Mock
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tableService = new TableServiceImpl(tableRepository);
        table = new Table();
        table.setId(ID_VALUE);
        table.setNumber(1);
    }

    @Test
    void getAllTables() {
        //given
        List<Table> tables = new ArrayList<>();
        tables.add(table);
        //when
        when(tableRepository.findAll()).thenReturn(tables);
        List<TableDTO> getAllTables = tableService.getAllTable();
        //then
        assertNotNull(getAllTables);
        assertEquals(tables.size(),getAllTables.size());
        verify(tableRepository, times(1)).findAll();
    }

    @Test
    void getTableById() {
        when(tableRepository.findById(ID_VALUE)).thenReturn(Optional.of(table));
        TableDTO tableDTO = tableService.getTableById(ID_VALUE);

        assertEquals(table.getId(), tableDTO.getId());
        verify(tableRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteTableById() {
        when(tableRepository.findById(ID_VALUE)).thenReturn(Optional.of(table));
        tableRepository.deleteById(ID_VALUE);

        verify(tableRepository, times(1)).deleteById(ID_VALUE);
        TableDTO tableDTO = tableService.deleteTableById(ID_VALUE);
        assertEquals(ID_VALUE, tableDTO.getId());
    }

    @Test
    void updateTableById() {
        TableDTO tableDTO = new TableDTO();
        tableDTO.setId(ID_VALUE);
        tableDTO.setNumber(1);

        when(tableRepository.findById(ID_VALUE)).thenReturn(Optional.of(table));
        when(tableRepository.save(table)).thenReturn(table);

        TableDTO updatedTable = tableService.update(tableDTO, ID_VALUE);
        assertEquals(updatedTable.getId(), table.getId());
        assertEquals(updatedTable.getNumber(), tableDTO.getNumber());
    }

    @Test
    void createTable() {
        Table createTable = new Table();
        createTable.setId(ID_VALUE);
        createTable.setNumber(ID_VALUE);

        TableDTO confirmTable = new TableDTO();
        confirmTable.setId(ID_VALUE);
        confirmTable.setNumber(ID_VALUE);

        when(tableRepository.save(createTable)).thenReturn(createTable);
        TableDTO createdTable = tableService.createTable(confirmTable);

        assertNotNull(createdTable);
        assertEquals(createdTable.getId(), createTable.getId());
        assertEquals(createdTable.getNumber(), createTable.getNumber());
    }
}
