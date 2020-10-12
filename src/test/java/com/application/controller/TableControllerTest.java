package com.application.controller;


import com.application.dto.TableDTO;
import com.application.service.dbImpl.TableServiceImpl;
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
@WebMvcTest(controllers = TableController.class)
class TableControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private TableController controller;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TableServiceImpl tableService;
    private TableDTO tableDTO;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //Setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        tableDTO = new TableDTO();
        tableDTO.setId(ID_VALUE);
        tableDTO.setNumber(1);
    }

    @Test
    void getAllTables() throws Exception {
        //given
        List<TableDTO> tableDTOList = new ArrayList<>();
        tableDTOList.add(tableDTO);
        //when
        when(tableService.getAllTable()).thenReturn(tableDTOList);
        //then
        mockMvc.perform(get("/api/v1/tables")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getTableById() throws Exception {
        //when
        when(tableService.getTableById(anyInt())).thenReturn(tableDTO);
        //then
        this.mockMvc.perform(get("/api/v1/tables/{tableId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void deleteTableById() throws Exception {
        //when
        when(tableService.deleteTableById(ID_VALUE)).thenReturn(tableDTO);
        //then
        this.mockMvc.perform(delete("/api/v1/tables/{tableId}", tableDTO.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(tableService, times(1)).deleteTableById(ID_VALUE);
    }

    @Test
    void updateTableById() throws Exception {
        //given
        TableDTO toUpdate = new TableDTO();
        toUpdate.setId(ID_VALUE);
        toUpdate.setNumber(ID_VALUE);
        //when
        Mockito.when(tableService.update(toUpdate, ID_VALUE)).thenReturn(toUpdate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/v1/tables/1", toUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toUpdate));
        mockMvc.perform(builder).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(ID_VALUE)))
                .andExpect(MockMvcResultMatchers.content()
                        .string(this.mapper.writeValueAsString(toUpdate)));
    }

    @Test
    void createTable() throws Exception {
        //given
        TableDTO toCreate = new TableDTO();
        toCreate.setNumber(ID_VALUE);
        toCreate.setId(ID_VALUE);
        //when
        when(tableService.createTable(Mockito.any(TableDTO.class))).thenReturn(toCreate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/tables").
                contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toCreate));
        mockMvc.perform(builder).andExpect(status().isCreated())
                .andExpect(jsonPath("$.number", is(ID_VALUE))).
                andExpect(MockMvcResultMatchers.content()
                        .string(this.mapper.writeValueAsString(toCreate)));
    }
}
