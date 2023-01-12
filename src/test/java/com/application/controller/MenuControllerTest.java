package com.application.controller;

import com.application.dto.MenuDTO;
import com.application.model.enums.CategoryType;
import com.application.service.dbImpl.MenuServiceImpl;
import com.application.utils.ExceptionController;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MenuController.class)
class MenuControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private MenuController menuController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MenuServiceImpl menuService;
    private MenuDTO menuDTO;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //Setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(menuController)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        menuDTO = new MenuDTO();
        menuDTO.setId(ID_VALUE);
        menuDTO.setName("Mushroom");
        menuDTO.setDescription("Mushroom");
        menuDTO.setCategoryType(CategoryType.PIZZA);
        menuDTO.setPrice(85);
    }

    @Test
    void getAllProducts() throws Exception {
        //given
        List<MenuDTO> menuDTOList = new ArrayList<>();
        menuDTOList.add(menuDTO);
        //when
        when(menuService.getAllProducts()).thenReturn(menuDTOList);
        //then
        mockMvc.perform(get("/api/v1/menus")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getProductById() throws Exception {
        //when
        when(menuService.getProductById(anyInt())).thenReturn(menuDTO);
        //then
        this.mockMvc.perform(get("/api/v1/menus/{productId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void deleteProductById() throws Exception {
        //when
        when(menuService.deleteProductById(ID_VALUE)).thenReturn(menuDTO);
        //then
        this.mockMvc.perform(delete("/api/v1/menus/{productId}", menuDTO.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(menuService, times(1)).deleteProductById(ID_VALUE);
    }

    @Test
    void updateProductById() throws Exception {
        //given
        ObjectMapper mapper = new ObjectMapper();
        MenuDTO toUpdate = new MenuDTO();
        toUpdate.setPrice(65);
        //when
        when(menuService.update(any(), anyInt())).thenReturn(toUpdate);
        //then
        this.mockMvc.perform(put("/api/v1/menus/{productId}", menuDTO.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(toUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("price", Matchers.is(toUpdate.getPrice())));
    }
    @Test
    void createProduct() throws Exception {
        //given
        MenuDTO toCreate=new MenuDTO();
        toCreate.setPrice(85);
        toCreate.setId(ID_VALUE);
        //when
        when(menuService.createProduct(Mockito.any(MenuDTO.class))).thenReturn(toCreate);
        //then
        MockHttpServletRequestBuilder builder= MockMvcRequestBuilders.post("/api/v1/menus").
                contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toCreate));
        mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.price",is(85))).
                andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(toCreate)));
    }
}
