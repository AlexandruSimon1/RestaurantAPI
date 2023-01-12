package com.application.controller;


import com.application.utils.ExceptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.application.dto.AdminDTO;
import org.hamcrest.Matchers;
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
import com.application.service.dbImpl.AdministratorServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//This type of testing is used in case when we don't have any kind of security in our REST API
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdministratorController.class)
class AdministratorControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private AdministratorController administratorController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdministratorServiceImpl administratorService;
    private AdminDTO adminDTO;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(administratorController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        adminDTO = new AdminDTO();
        adminDTO.setId(ID_VALUE);
        adminDTO.setFirstName("Marcus");
        adminDTO.setLastName("Polo");
        adminDTO.setAddress("Istanbul");
        adminDTO.setPhoneNumber(65658965463L);
        adminDTO.setEmail("marcus@polo.com");
    }

    @Test
    void getAllAdministrators() throws Exception {
        //given
        List<AdminDTO> adminDTOList = new ArrayList<>();
        adminDTOList.add(adminDTO);
        //when
        when(administratorService.getAllAdministrators()).thenReturn(adminDTOList);
        //then
        mockMvc.perform(get("/api/v1/administrators")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAdministratorById() throws Exception {
        //when
        when(administratorService.getAdministratorById(anyInt())).thenReturn(adminDTO);
        //then
        this.mockMvc.perform(get("/api/v1/administrators/{administratorId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void deleteAdministratorById() throws Exception {
        //when
        when(administratorService.deleteAdministratorById(ID_VALUE)).thenReturn(adminDTO);
        //then
        this.mockMvc.perform(delete("/api/v1/administrators/{administratorId}", adminDTO.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(administratorService, times(1)).deleteAdministratorById(ID_VALUE);
    }

    @Test
    void updateAdministratorByID() throws Exception {
        //given
        ObjectMapper mapper = new ObjectMapper();
        AdminDTO toUpdate = new AdminDTO();
        toUpdate.setPhoneNumber(9564632639l);
        //when
        when(administratorService.update(any(), anyInt())).thenReturn(toUpdate);
        //then
        this.mockMvc.perform(put("/api/v1/administrators/{administratorId}", adminDTO.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("phoneNumber", Matchers.is(toUpdate.getPhoneNumber())));
    }

    @Test
    void createAdministrator() throws Exception {
        //given
        AdminDTO toCreate = new AdminDTO();
        toCreate.setFirstName("Alex");
        toCreate.setLastName("Rock");
        toCreate.setAddress("Moscow");
        //when
        when(administratorService.createAdministrator(Mockito.any(AdminDTO.class))).thenReturn(toCreate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/administrators").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toCreate));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("Alex"))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(toCreate)));
    }
}
