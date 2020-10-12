package com.application.controller;

import com.application.dto.TableDTO;
import com.application.service.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tables")
@Api(value = "tables", description = "CRUD Operations for Tables", tags = "TABLES")
public class TableController {
    private final TableService tableService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ALL TABLES", notes = "\n" + "This operation gets all tables")
    public List<TableDTO> getAllTables() {
        return tableService.getAllTable();
    }

    @GetMapping("/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET TABLE BY ID", notes = "\n" + "This operation get a table by id")
    public TableDTO getTableById(@PathVariable int tableId) {
        return tableService.getTableById(tableId);
    }

    @DeleteMapping("/{tableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE TABLE BY ID", notes = "\n" + "This operation deletes a table by id")
    public TableDTO deleteTableById(@PathVariable int tableId) {
        return tableService.deleteTableById(tableId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "CREATE TABLE", notes = "\n" + "This operation creates a table")
    public TableDTO createTable(@RequestBody TableDTO tableDTO) {
        return tableService.createTable(tableDTO);
    }

    @PutMapping("/{tableId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "UPDATE TABLE", notes = "\n" + "This operation updates a table by id")
    public TableDTO updateTableById(@PathVariable int tableId, @RequestBody TableDTO tableDTO) {
        return tableService.update(tableDTO, tableId);
    }
}
