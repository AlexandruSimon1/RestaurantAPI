package com.application.controller;

import com.application.dto.CheckOutDTO;
import com.application.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/checkout")
public class CheckOutController {
    private final CheckOutService checkOutService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CheckOutDTO> getAllCheckOut() {
        return checkOutService.getAllCheckOut();
    }

    @GetMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.OK)
    public CheckOutDTO getCheckOutById(@PathVariable int checkoutId) {
        return checkOutService.getCheckOutById(checkoutId);
    }

    @DeleteMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CheckOutDTO deleteCheckOutById(@PathVariable int checkoutId) {
        return checkOutService.deleteCheckOutById(checkoutId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckOutDTO createCheckOut(@RequestBody CheckOutDTO checkOutDTO) {
        return checkOutService.createCheckOut(checkOutDTO);
    }

    @PutMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CheckOutDTO updateCheckOutById(@PathVariable int checkoutId, @RequestBody CheckOutDTO checkOutDTO) {
        return checkOutService.update(checkOutDTO, checkoutId);
    }
}
