package com.application.controller;

import com.application.dto.CheckOutDTO;
import com.application.service.CheckOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/checkout")
@Api(value = "checkout", description = "CRUD Operations for CheckOut", tags = "CHECKOUT")
public class CheckOutController {
    private final CheckOutService checkOutService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ALL CHECKOUT", notes = "\n" + "This operation gets all checkout")
    public List<CheckOutDTO> getAllCheckOut() {
        return checkOutService.getAllCheckOut();
    }

    @GetMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET CHECKOUT BY ID", notes = "\n" + "This operation get a checkout by id")
    public CheckOutDTO getCheckOutById(@PathVariable int checkoutId) {
        return checkOutService.getCheckOutById(checkoutId);
    }

    @DeleteMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE CHECKOUT BY ID", notes = "\n" + "This operation delete a checkout by id")
    public CheckOutDTO deleteCheckOutById(@PathVariable int checkoutId) {
        return checkOutService.deleteCheckOutById(checkoutId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "CREATE CHECKOUT", notes = "\n" + "This operation creates a checkout")
    public CheckOutDTO createCheckOut(@RequestBody CheckOutDTO checkOutDTO) {
        return checkOutService.createCheckOut(checkOutDTO);
    }

    @PutMapping("/{checkoutId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "UPDATE CHECKOUT BY ID", notes = "\n" + "This operation updates a checkout by id")
    public CheckOutDTO updateCheckOutById(@PathVariable int checkoutId, @RequestBody CheckOutDTO checkOutDTO) {
        return checkOutService.update(checkOutDTO, checkoutId);
    }
}
