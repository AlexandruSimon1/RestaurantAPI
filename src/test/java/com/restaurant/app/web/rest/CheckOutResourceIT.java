package com.restaurant.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.app.IntegrationTest;
import com.restaurant.app.domain.CheckOut;
import com.restaurant.app.domain.Order;
import com.restaurant.app.repository.CheckOutRepository;
import com.restaurant.app.service.criteria.CheckOutCriteria;
import com.restaurant.app.service.dto.CheckOutDTO;
import com.restaurant.app.service.mapper.CheckOutMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CheckOutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckOutResourceIT {

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/check-outs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private CheckOutMapper checkOutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckOutMockMvc;

    private CheckOut checkOut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckOut createEntity(EntityManager em) {
        CheckOut checkOut = new CheckOut().paymentType(DEFAULT_PAYMENT_TYPE);
        return checkOut;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckOut createUpdatedEntity(EntityManager em) {
        CheckOut checkOut = new CheckOut().paymentType(UPDATED_PAYMENT_TYPE);
        return checkOut;
    }

    @BeforeEach
    public void initTest() {
        checkOut = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckOut() throws Exception {
        int databaseSizeBeforeCreate = checkOutRepository.findAll().size();
        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);
        restCheckOutMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeCreate + 1);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void createCheckOutWithExistingId() throws Exception {
        // Create the CheckOut with an existing ID
        checkOut.setId(1L);
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        int databaseSizeBeforeCreate = checkOutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckOutMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckOuts() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)));
    }

    @Test
    @Transactional
    void getCheckOut() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get the checkOut
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL_ID, checkOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkOut.getId().intValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE));
    }

    @Test
    @Transactional
    void getCheckOutsByIdFiltering() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        Long id = checkOut.getId();

        defaultCheckOutShouldBeFound("id.equals=" + id);
        defaultCheckOutShouldNotBeFound("id.notEquals=" + id);

        defaultCheckOutShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckOutShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckOutShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckOutShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckOutsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultCheckOutShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the checkOutList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultCheckOutShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllCheckOutsByPaymentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList where paymentType not equals to DEFAULT_PAYMENT_TYPE
        defaultCheckOutShouldNotBeFound("paymentType.notEquals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the checkOutList where paymentType not equals to UPDATED_PAYMENT_TYPE
        defaultCheckOutShouldBeFound("paymentType.notEquals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllCheckOutsByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultCheckOutShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the checkOutList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultCheckOutShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllCheckOutsByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList where paymentType is not null
        defaultCheckOutShouldBeFound("paymentType.specified=true");

        // Get all the checkOutList where paymentType is null
        defaultCheckOutShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckOutsByPaymentTypeContainsSomething() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList where paymentType contains DEFAULT_PAYMENT_TYPE
        defaultCheckOutShouldBeFound("paymentType.contains=" + DEFAULT_PAYMENT_TYPE);

        // Get all the checkOutList where paymentType contains UPDATED_PAYMENT_TYPE
        defaultCheckOutShouldNotBeFound("paymentType.contains=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllCheckOutsByPaymentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        // Get all the checkOutList where paymentType does not contain DEFAULT_PAYMENT_TYPE
        defaultCheckOutShouldNotBeFound("paymentType.doesNotContain=" + DEFAULT_PAYMENT_TYPE);

        // Get all the checkOutList where paymentType does not contain UPDATED_PAYMENT_TYPE
        defaultCheckOutShouldBeFound("paymentType.doesNotContain=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllCheckOutsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        em.persist(order);
        em.flush();
        checkOut.setOrder(order);
        checkOutRepository.saveAndFlush(checkOut);
        Long orderId = order.getId();

        // Get all the checkOutList where order equals to orderId
        defaultCheckOutShouldBeFound("orderId.equals=" + orderId);

        // Get all the checkOutList where order equals to (orderId + 1)
        defaultCheckOutShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckOutShouldBeFound(String filter) throws Exception {
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)));

        // Check, that the count call also returns 1
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckOutShouldNotBeFound(String filter) throws Exception {
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckOutMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckOut() throws Exception {
        // Get the checkOut
        restCheckOutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckOut() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();

        // Update the checkOut
        CheckOut updatedCheckOut = checkOutRepository.findById(checkOut.getId()).get();
        // Disconnect from session so that the updates on updatedCheckOut are not directly saved in db
        em.detach(updatedCheckOut);
        updatedCheckOut.paymentType(UPDATED_PAYMENT_TYPE);
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(updatedCheckOut);

        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkOutDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkOutDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckOutWithPatch() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();

        // Update the checkOut using partial update
        CheckOut partialUpdatedCheckOut = new CheckOut();
        partialUpdatedCheckOut.setId(checkOut.getId());

        partialUpdatedCheckOut.paymentType(UPDATED_PAYMENT_TYPE);

        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckOut.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckOut))
            )
            .andExpect(status().isOk());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCheckOutWithPatch() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();

        // Update the checkOut using partial update
        CheckOut partialUpdatedCheckOut = new CheckOut();
        partialUpdatedCheckOut.setId(checkOut.getId());

        partialUpdatedCheckOut.paymentType(UPDATED_PAYMENT_TYPE);

        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckOut.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckOut))
            )
            .andExpect(status().isOk());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
        CheckOut testCheckOut = checkOutList.get(checkOutList.size() - 1);
        assertThat(testCheckOut.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkOutDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckOut() throws Exception {
        int databaseSizeBeforeUpdate = checkOutRepository.findAll().size();
        checkOut.setId(count.incrementAndGet());

        // Create the CheckOut
        CheckOutDTO checkOutDTO = checkOutMapper.toDto(checkOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckOutMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkOutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckOut in the database
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckOut() throws Exception {
        // Initialize the database
        checkOutRepository.saveAndFlush(checkOut);

        int databaseSizeBeforeDelete = checkOutRepository.findAll().size();

        // Delete the checkOut
        restCheckOutMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkOut.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckOut> checkOutList = checkOutRepository.findAll();
        assertThat(checkOutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
