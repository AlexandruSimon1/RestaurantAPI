package com.application.service;

import com.application.dto.WaiterDTO;

import java.util.List;

public interface WaiterService {
    List<WaiterDTO> getAllWaiters();

    WaiterDTO getWaiterById(int waiterId);

    WaiterDTO deleteWaiterById(int waiterId);

    WaiterDTO createWaiter(WaiterDTO waiterDTO);

    WaiterDTO update(WaiterDTO waiterDTO, int waiterId);

//    Set<OrderDTO> findAllOrders(int waiterId);
}
