package com.application.controller;

import com.application.dto.WaiterDTO;
import com.application.service.WaiterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/waiters")
@Api(value = "waiter", description = "CRUD Operations for Waiters", tags = "WAITER")
public class WaiterController {
    private final WaiterService waiterService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ALL WAITERS", notes = "\n" + "This operation gets all waiters")
    public List<WaiterDTO> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

    @GetMapping("/{waiterId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET WAITER BY ID", notes = "\n" + "This operation get an waiter by id")
    public WaiterDTO getWaiterById(@PathVariable int waiterId) {
        return waiterService.getWaiterById(waiterId);
    }

    @DeleteMapping("/{waiterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE WAITER BY ID", notes = "\n" + "This operation delete an existing waiter by id")
    public WaiterDTO deleteWaiterById(@PathVariable int waiterId) {
        return waiterService.deleteWaiterById(waiterId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "CREATE A WAITER", notes = "\n" + "This operation create a new waiter")
    public WaiterDTO createWaiter(@RequestBody WaiterDTO waiterDTO) {
        return waiterService.createWaiter(waiterDTO);
    }

    @PutMapping("/{waiterId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "UPDATE WAITER BY ID", notes = "\n" + "This operation update an existing waiter by id")
    public WaiterDTO updateWaiterById(@PathVariable int waiterId, @RequestBody WaiterDTO waiterDTO) {
        return waiterService.update(waiterDTO, waiterId);
    }
}
