package com.application.service.dbImpl;

import com.application.dto.WaiterDTO;
import com.application.model.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.application.repository.WaiterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaiterServiceImplTest {
    private static final int ID_VALUE = 1;
    private WaiterServiceImpl waiterServiceImpl;
    private Waiter defaultWaiter;
    @Mock
    private WaiterRepository waiterRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        waiterServiceImpl = new WaiterServiceImpl(waiterRepository);

        defaultWaiter = new Waiter();
        defaultWaiter.setId(ID_VALUE);
        defaultWaiter.setFirstName("Peter");
        defaultWaiter.setLastName("Strawberry");
        defaultWaiter.setAddress("Tokyo");
        defaultWaiter.setPhoneNumber(6589564632564L);
        defaultWaiter.setEmail("peter@strawberry.com");
    }

    @Test
    void getAllWaiters() {
        //given
        List<Waiter> getAllWaiters = new ArrayList<>();
        getAllWaiters.add(defaultWaiter);
        //when
        when(waiterRepository.findAll()).thenReturn(getAllWaiters);
        List<WaiterDTO> waiterDTOS = waiterServiceImpl.getAllWaiters();
        //then
        assertNotNull(waiterDTOS);
        assertEquals(getAllWaiters.size(), waiterDTOS.size());
        verify(waiterRepository, times(1)).findAll();
    }

    @Test
    void getWaiterById() {
        when(waiterRepository.findById(ID_VALUE)).thenReturn(Optional.of(defaultWaiter));
        WaiterDTO waiterDTO = waiterServiceImpl.getWaiterById(ID_VALUE);

        assertEquals(defaultWaiter.getId(), waiterDTO.getId());
        verify(waiterRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteWaiterById() {
        when(waiterRepository.findById(ID_VALUE)).thenReturn(Optional.of(defaultWaiter));
        waiterRepository.deleteById(ID_VALUE);
        WaiterDTO waiterDTO = waiterServiceImpl.deleteWaiterById(ID_VALUE);

        assertEquals(ID_VALUE, waiterDTO.getId());
        verify(waiterRepository, times(1)).deleteById(ID_VALUE);
    }

    @Test
    void updateWaiterById() {
        WaiterDTO updateWaiter = new WaiterDTO();
        updateWaiter.setFirstName("Nick");
        updateWaiter.setLastName("Blackberry");
        updateWaiter.setAddress("Amsterdam");
        updateWaiter.setPhoneNumber(25478965789L);
        updateWaiter.setEmail("nick@blackberry.com");

        when(waiterRepository.findById(ID_VALUE)).thenReturn(Optional.of(defaultWaiter));
        when(waiterRepository.save(defaultWaiter)).thenReturn(defaultWaiter);
        WaiterDTO updatedWaiter = waiterServiceImpl.update(updateWaiter, ID_VALUE);

        assertEquals(updatedWaiter.getId(), defaultWaiter.getId());
        assertEquals(updatedWaiter.getFirstName(), updateWaiter.getFirstName());
        assertEquals(updatedWaiter.getLastName(), updateWaiter.getLastName());
        assertEquals(updatedWaiter.getAddress(), updateWaiter.getAddress());
        assertEquals(updatedWaiter.getPhoneNumber(), updateWaiter.getPhoneNumber());
        assertEquals(updatedWaiter.getEmail(), updateWaiter.getEmail());
    }

    @Test
    void createWaiter() {
        Waiter newWaiter=new Waiter();
        newWaiter.setId(2);
        newWaiter.setFirstName("Mike");
        newWaiter.setLastName("Angus");
        newWaiter.setAddress("Venice");
        newWaiter.setPhoneNumber(98564578563L);
        newWaiter.setEmail("mike@angus.com");

        WaiterDTO createWaiter = new WaiterDTO();
        createWaiter.setId(2);
        createWaiter.setFirstName("Mike");
        createWaiter.setLastName("Angus");
        createWaiter.setAddress("Venice");
        createWaiter.setPhoneNumber(98564578563L);
        createWaiter.setEmail("mike@angus.com");

        when(waiterRepository.save(newWaiter)).thenReturn(newWaiter);
        WaiterDTO createdWaiter = waiterServiceImpl.createWaiter(createWaiter);

        assertNotNull(createdWaiter);
        assertEquals(createdWaiter.getId(), newWaiter.getId());
        assertEquals(createdWaiter.getFirstName(),newWaiter.getFirstName());
        assertEquals(createdWaiter.getLastName(), newWaiter.getLastName());
        assertEquals(createdWaiter.getAddress(), newWaiter.getAddress());
        assertEquals(createdWaiter.getPhoneNumber(), newWaiter.getPhoneNumber());
        assertEquals(createdWaiter.getEmail(),newWaiter.getEmail());
    }
}
