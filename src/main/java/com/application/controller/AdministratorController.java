package com.application.controller;

import com.application.dto.AdminDTO;
import com.application.service.AdministratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/administrators")
@Api(value = "administrator", description = "CRUD Operations for Administrators", tags = "ADMINISTRATOR")
public class AdministratorController {
    @Autowired
    private final AdministratorService administratorService;
    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    //This is a response information that we will get using our Swagger and Swagger documentation
    @ApiOperation(value = "GET ALL ADMINISTRATORS", notes = "\n" + "This operation gets all administrators")
    public List<AdminDTO> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @GetMapping(value = "/{administratorId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ADMINISTRATOR BY ID", notes = "\n" + "This operation get an administrator by id")
    public AdminDTO getAdministratorById(@PathVariable int administratorId) {
        return administratorService.getAdministratorById(administratorId);
    }

    @DeleteMapping(value = "/{administratorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE ADMINISTRATOR BY ID", notes = "\n" + "This operation deletes an administrator by id")
    public AdminDTO deleteAdministratorById(@PathVariable Integer administratorId) {
        return administratorService.deleteAdministratorById(administratorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "CREATE ADMINISTRATOR", notes = "\n" + "This operation creates an administrator")
    public AdminDTO createAdmin(@RequestBody AdminDTO adminDTO) {
        return administratorService.createAdministrator(adminDTO);
    }

    @PutMapping(value = "/{administratorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "UPDATE ADMINISTRATOR BY ID", notes = "\n" + "This operation update an administrator by id")
    public AdminDTO updateAdministratorById(@PathVariable Integer administratorId, @RequestBody AdminDTO adminDTO) {
        return administratorService.update(adminDTO, administratorId);
    }


}
