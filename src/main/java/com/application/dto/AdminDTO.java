package com.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private Long phoneNumber;
    private String email;
}
