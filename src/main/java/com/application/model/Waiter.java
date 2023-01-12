package com.application.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "waiters")
public class Waiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "email")
    private String email;
}
