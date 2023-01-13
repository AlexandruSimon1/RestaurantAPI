package com.application.controller;

import com.application.dto.AdminDTO;
import com.application.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/administrators")
public class AdministratorController {
    @Autowired
    private final AdministratorService administratorService;

    //Mapping name
    @GetMapping
    //Response status is used for providing the status of our request
    @ResponseStatus(HttpStatus.OK)
    //This is a response information that we will get using our Swagger and Swagger documentation
    public List<AdminDTO> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @GetMapping(value = "/{administratorId}")
    @ResponseStatus(HttpStatus.OK)
    public AdminDTO getAdministratorById(@PathVariable int administratorId) {
        return administratorService.getAdministratorById(administratorId);
    }

    @DeleteMapping(value = "/{administratorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AdminDTO deleteAdministratorById(@PathVariable Integer administratorId) {
        return administratorService.deleteAdministratorById(administratorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminDTO createAdmin(@RequestBody AdminDTO adminDTO) {
        return administratorService.createAdministrator(adminDTO);
    }

    @PutMapping(value = "/{administratorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AdminDTO updateAdministratorById(@PathVariable Integer administratorId, @RequestBody AdminDTO adminDTO) {
        return administratorService.update(adminDTO, administratorId);
    }
}
