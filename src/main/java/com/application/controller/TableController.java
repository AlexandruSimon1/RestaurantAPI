package com.application.controller;

import com.application.dto.TableDTO;
import com.application.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tables")
public class TableController {
    private final TableService tableService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TableDTO> getAllTables() {
        return tableService.getAllTable();
    }

    @GetMapping("/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    public TableDTO getTableById(@PathVariable int tableId) {
        return tableService.getTableById(tableId);
    }

    @DeleteMapping("/{tableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TableDTO deleteTableById(@PathVariable int tableId) {
        return tableService.deleteTableById(tableId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TableDTO createTable(@RequestBody TableDTO tableDTO) {
        return tableService.createTable(tableDTO);
    }

    @PutMapping("/{tableId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TableDTO updateTableById(@PathVariable int tableId, @RequestBody TableDTO tableDTO) {
        return tableService.update(tableDTO, tableId);
    }
}
