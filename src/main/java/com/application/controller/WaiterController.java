package com.application.controller;

import com.application.dto.WaiterDTO;
import com.application.service.WaiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/waiters")
public class WaiterController {
    private final WaiterService waiterService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WaiterDTO> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

    @GetMapping("/{waiterId}")
    @ResponseStatus(HttpStatus.OK)
    public WaiterDTO getWaiterById(@PathVariable int waiterId) {
        return waiterService.getWaiterById(waiterId);
    }

    @DeleteMapping("/{waiterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public WaiterDTO deleteWaiterById(@PathVariable int waiterId) {
        return waiterService.deleteWaiterById(waiterId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WaiterDTO createWaiter(@RequestBody WaiterDTO waiterDTO) {
        return waiterService.createWaiter(waiterDTO);
    }

    @PutMapping("/{waiterId}")
    @ResponseStatus(HttpStatus.OK)
    public WaiterDTO updateWaiterById(@PathVariable int waiterId, @RequestBody WaiterDTO waiterDTO) {
        return waiterService.update(waiterDTO, waiterId);
    }
}
