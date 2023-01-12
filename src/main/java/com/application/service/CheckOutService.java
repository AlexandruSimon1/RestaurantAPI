package com.application.service;

import com.application.dto.CheckOutDTO;

import java.util.List;

public interface CheckOutService {
    List<CheckOutDTO> getAllCheckOut();

    CheckOutDTO getCheckOutById(int checkOutId);

    CheckOutDTO deleteCheckOutById(int checkOutId);

    CheckOutDTO createCheckOut(CheckOutDTO checkOutDTO);

    CheckOutDTO update(CheckOutDTO checkOutDTO, int checkOutId);
}
