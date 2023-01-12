package com.application.controller;

import com.application.dto.CheckOutDTO;
import com.application.service.dbImpl.CheckOutServiceImpl;
import com.application.utils.ExceptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//This type of testing is used in case when we don't have any kind of security in our REST API
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CheckOutController.class)
class CheckOutControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private CheckOutController controller;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CheckOutServiceImpl checkOutService;
    private CheckOutDTO checkOutDTO;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).build();
        //Inserting the data in order to be able to do the test of the endpoints
        checkOutDTO = new CheckOutDTO();
        checkOutDTO.setId(ID_VALUE);
        checkOutDTO.setPaymentType("Visa");
    }

    @Test
    void getAllCheckOut() throws Exception {
        //given
        List<CheckOutDTO> checkOutDTOList = new ArrayList<>();
        checkOutDTOList.add(checkOutDTO);
        //when
        when(checkOutService.getAllCheckOut()).thenReturn(checkOutDTOList);
        //then
        mockMvc.perform(get("/api/v1/checkout")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getCheckOutById() throws Exception {
        //when
        when(checkOutService.getCheckOutById(anyInt())).thenReturn(checkOutDTO);
        //then
        this.mockMvc.perform(get("/api/v1/checkout/{checkoutId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void deleteCheckOutById() throws Exception {
        //when
        when(checkOutService.deleteCheckOutById(ID_VALUE)).thenReturn(checkOutDTO);
        //then
        this.mockMvc.perform(delete("/api/v1/checkout/{checkoutId}", checkOutDTO.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(checkOutService, times(1)).deleteCheckOutById(ID_VALUE);
    }

    @Test
    void updateCheckOutById() throws Exception {
        //given
        CheckOutDTO toUpdate = new CheckOutDTO();
        toUpdate.setId(ID_VALUE);
        //when
        Mockito.when(checkOutService.update(toUpdate, ID_VALUE)).thenReturn(toUpdate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/v1/checkout/1", toUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toUpdate));
        mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.id", is(ID_VALUE)))
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(toUpdate)));
    }

    @Test
    void createCheckOut() throws Exception {
        //given
        CheckOutDTO toCreate = new CheckOutDTO();
        toCreate.setPaymentType("Visa");
        toCreate.setId(ID_VALUE);
        //when
        when(checkOutService.createCheckOut(Mockito.any(CheckOutDTO.class))).thenReturn(toCreate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/checkout").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toCreate));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.paymentType", is("Visa"))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(toCreate)));
    }
}
