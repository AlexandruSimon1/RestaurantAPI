package com.application.controller;

import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.model.enums.CategoryType;
import com.application.service.dbImpl.OrderServiceImpl;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    private static final int ID_VALUE = 1;
    @Autowired
    private OrderController controller;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderServiceImpl orderService;
    private OrderDTO orderDTO;
    private List<MenuDTO> menuDTO;
    private MenuDTO testMenuDTO;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        //Setup the controller to MockMvc in order to have access to the information from the REST API
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionController())
                .alwaysExpect(MockMvcResultMatchers.content().
                        contentType(MediaType.APPLICATION_JSON))
                .build();
        //Inserting the data in order to be able to do the test of the endpoints
        orderDTO = new OrderDTO();
        orderDTO.setId(ID_VALUE);
        orderDTO.setOrderNumber(1);
        orderDTO.setMenus(menuDTO);

        menuDTO = new ArrayList<>();
        testMenuDTO = new MenuDTO();
        testMenuDTO.setId(ID_VALUE);
        testMenuDTO.setName("Pizza");
        testMenuDTO.setCategoryType(CategoryType.PIZZA);
        testMenuDTO.setPrice(85);
        menuDTO.add(testMenuDTO);
    }

    @Test
    void getAllOrders() throws Exception {
        //given
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDTOList.add(orderDTO);
        //when
        when(orderService.getAllOrders()).thenReturn(orderDTOList);
        //then
        mockMvc.perform(get("/api/v1/orders")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getOrderById() throws Exception {
        //when
        when(orderService.getOrderById(anyInt())).thenReturn(orderDTO);
        //then
        this.mockMvc.perform(get("/api/v1/orders/{orderId}", ID_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(ID_VALUE)));
    }

    @Test
    void deleteOrderById() throws Exception {
        //when
        when(orderService.deleteOrderById(ID_VALUE)).thenReturn(orderDTO);
        //then
        this.mockMvc.perform(delete("/api/v1/orders/{orderId}", orderDTO.getId()))
                .andExpect(status().is2xxSuccessful());
        verify(orderService, times(1)).deleteOrderById(ID_VALUE);
    }

    @Test
    void updateOrderById() throws Exception {
        //given
        OrderDTO toUpdate = new OrderDTO();
        toUpdate.setId(ID_VALUE);
        toUpdate.setOrderNumber(ID_VALUE);
        //when
        Mockito.when(orderService.update(toUpdate, ID_VALUE)).thenReturn(toUpdate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/v1/orders/1", toUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toUpdate));
        mockMvc.perform(builder).andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(ID_VALUE)))
                .andExpect(MockMvcResultMatchers.content()
                        .string(this.mapper.writeValueAsString(toUpdate)));
    }

    @Test
    void createOrder() throws Exception {
        //given
        OrderDTO toCreate = new OrderDTO();
        toCreate.setOrderNumber(ID_VALUE);
        toCreate.setId(ID_VALUE);
        //when
        when(orderService.createOrder(Mockito.any(OrderDTO.class))).thenReturn(toCreate);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(toCreate));
        mockMvc.perform(builder).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(ID_VALUE))).
                andExpect(MockMvcResultMatchers.content()
                        .string(this.mapper.writeValueAsString(toCreate)));
    }

    @Test
    void getAllProductByOrderId() throws Exception {
        //when
        when(orderService.findAllProductByOrderId(ID_VALUE)).thenReturn(menuDTO);
        //then
        this.mockMvc.perform(get("/api/v1/orders/{orderId}/menus", ID_VALUE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void updateProductByOrderId() throws Exception {
        //when
        Mockito.when(orderService.updateProductByOrderId(ID_VALUE, ID_VALUE, testMenuDTO)).thenReturn(testMenuDTO);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/v1/orders/1/menus/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(testMenuDTO));
        mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.id", is(ID_VALUE)))
                .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(testMenuDTO)));
    }

    @Test
    void deleteProductByOrderId() throws Exception {
        //when
        Mockito.when(orderService.deleteProductByOrderId(ID_VALUE, ID_VALUE)).thenReturn(testMenuDTO);
        //then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/v1/orders/1/menus/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(testMenuDTO));
        mockMvc.perform(builder).andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content()
                        .string(this.mapper.writeValueAsString(testMenuDTO)));
    }
}
