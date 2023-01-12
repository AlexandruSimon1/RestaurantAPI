package com.application.service.dbImpl;

import com.application.dto.CheckOutDTO;
import com.application.model.CheckOut;
import com.application.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.application.repository.CheckOutRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckOutServiceImplTest {
    private static final int ID_VALUE = 1;
    private CheckOutServiceImpl checkOutService;
    private Order order;
    private CheckOut checkOut;

    @Mock
    private CheckOutRepository checkOutRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        checkOutService = new CheckOutServiceImpl(checkOutRepository);
        checkOut = new CheckOut();
        checkOut.setId(ID_VALUE);
        checkOut.setPaymentType("Visa");
    }

    @Test
    void getAllCheckOut() {
        //given
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkOut);
        //when
        when(checkOutRepository.findAll()).thenReturn(checkOuts);
        List<CheckOutDTO> checkOutDTOS = checkOutService.getAllCheckOut();
        //then
        assertNotNull(checkOutDTOS);
        assertEquals(checkOuts.size(), checkOutDTOS.size());
        verify(checkOutRepository, times(1)).findAll();
    }

    @Test
    void getCheckOutById() {
        //when
        when(checkOutRepository.findById(ID_VALUE)).thenReturn(Optional.of(checkOut));
        CheckOutDTO checkOutDTO = checkOutService.getCheckOutById(ID_VALUE);
        //then
        assertEquals(checkOut.getId(), checkOutDTO.getId());
        assertEquals(checkOut.getPaymentType(), checkOutDTO.getPaymentType());
        verify(checkOutRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteCheckOutByID() {
        when(checkOutRepository.findById(ID_VALUE)).thenReturn(Optional.of(checkOut));
        checkOutRepository.deleteById(ID_VALUE);

        verify(checkOutRepository, times(1)).deleteById(ID_VALUE);
        CheckOutDTO checkOutDTO = checkOutService.deleteCheckOutById(ID_VALUE);
        assertEquals(ID_VALUE, checkOutDTO.getId());
    }

    @Test
    void updateCheckOutById() {
        CheckOutDTO checkOutDTO = new CheckOutDTO();
        checkOutDTO.setId(ID_VALUE);
        checkOutDTO.setPaymentType("Visa");

        when(checkOutRepository.findById(ID_VALUE)).thenReturn(Optional.of(checkOut));
        when(checkOutRepository.save(checkOut)).thenReturn(checkOut);

        CheckOutDTO updatedCheckOut = checkOutService.update(checkOutDTO, ID_VALUE);
        assertEquals(updatedCheckOut.getId(), checkOut.getId());
        assertEquals(updatedCheckOut.getPaymentType(), checkOut.getPaymentType());
    }

    @Test
    void createCheckOut() {
        CheckOut checkOutCreate = new CheckOut();
        checkOutCreate.setId(ID_VALUE);
        checkOutCreate.setPaymentType("MasterCard");

        CheckOutDTO checkOutDTO = new CheckOutDTO();
        checkOutDTO.setId(ID_VALUE);
        checkOutDTO.setPaymentType("MasterCard");

        when(checkOutRepository.save(checkOutCreate)).thenReturn(checkOutCreate);
        CheckOutDTO createCheckOut = checkOutService.createCheckOut(checkOutDTO);

        assertNotNull(createCheckOut);
        assertEquals(createCheckOut.getId(), checkOutCreate.getId());
        assertEquals(createCheckOut.getPaymentType(), checkOutCreate.getPaymentType());
    }
}
