package com.restaurant.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.app.IntegrationTest;
import com.restaurant.app.domain.Order;
import com.restaurant.app.domain.Table;
import com.restaurant.app.repository.TableRepository;
import com.restaurant.app.service.criteria.TableCriteria;
import com.restaurant.app.service.dto.TableDTO;
import com.restaurant.app.service.mapper.TableMapper;
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
 * Integration tests for the {@link TableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TableResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final Integer SMALLER_NUMBER = 1 - 1;

    private static final String ENTITY_API_URL = "/api/tables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTableMockMvc;

    private Table table;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table createEntity(EntityManager em) {
        Table table = new Table().number(DEFAULT_NUMBER);
        return table;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Table createUpdatedEntity(EntityManager em) {
        Table table = new Table().number(UPDATED_NUMBER);
        return table;
    }

    @BeforeEach
    public void initTest() {
        table = createEntity(em);
    }

    @Test
    @Transactional
    void createTable() throws Exception {
        int databaseSizeBeforeCreate = tableRepository.findAll().size();
        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);
        restTableMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeCreate + 1);
        Table testTable = tableList.get(tableList.size() - 1);
        assertThat(testTable.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    void createTableWithExistingId() throws Exception {
        // Create the Table with an existing ID
        table.setId(1L);
        TableDTO tableDTO = tableMapper.toDto(table);

        int databaseSizeBeforeCreate = tableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTables() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList
        restTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(table.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    void getTable() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get the table
        restTableMockMvc
            .perform(get(ENTITY_API_URL_ID, table.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(table.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    void getTablesByIdFiltering() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        Long id = table.getId();

        defaultTableShouldBeFound("id.equals=" + id);
        defaultTableShouldNotBeFound("id.notEquals=" + id);

        defaultTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTableShouldNotBeFound("id.greaterThan=" + id);

        defaultTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTableShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number equals to DEFAULT_NUMBER
        defaultTableShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the tableList where number equals to UPDATED_NUMBER
        defaultTableShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number not equals to DEFAULT_NUMBER
        defaultTableShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the tableList where number not equals to UPDATED_NUMBER
        defaultTableShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultTableShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the tableList where number equals to UPDATED_NUMBER
        defaultTableShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number is not null
        defaultTableShouldBeFound("number.specified=true");

        // Get all the tableList where number is null
        defaultTableShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number is greater than or equal to DEFAULT_NUMBER
        defaultTableShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the tableList where number is greater than or equal to UPDATED_NUMBER
        defaultTableShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number is less than or equal to DEFAULT_NUMBER
        defaultTableShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the tableList where number is less than or equal to SMALLER_NUMBER
        defaultTableShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number is less than DEFAULT_NUMBER
        defaultTableShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the tableList where number is less than UPDATED_NUMBER
        defaultTableShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        // Get all the tableList where number is greater than DEFAULT_NUMBER
        defaultTableShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the tableList where number is greater than SMALLER_NUMBER
        defaultTableShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllTablesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);
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
        table.setOrder(order);
        tableRepository.saveAndFlush(table);
        Long orderId = order.getId();

        // Get all the tableList where order equals to orderId
        defaultTableShouldBeFound("orderId.equals=" + orderId);

        // Get all the tableList where order equals to (orderId + 1)
        defaultTableShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTableShouldBeFound(String filter) throws Exception {
        restTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(table.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));

        // Check, that the count call also returns 1
        restTableMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTableShouldNotBeFound(String filter) throws Exception {
        restTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTableMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTable() throws Exception {
        // Get the table
        restTableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTable() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        int databaseSizeBeforeUpdate = tableRepository.findAll().size();

        // Update the table
        Table updatedTable = tableRepository.findById(table.getId()).get();
        // Disconnect from session so that the updates on updatedTable are not directly saved in db
        em.detach(updatedTable);
        updatedTable.number(UPDATED_NUMBER);
        TableDTO tableDTO = tableMapper.toDto(updatedTable);

        restTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tableDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isOk());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
        Table testTable = tableList.get(tableList.size() - 1);
        assertThat(testTable.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();
        table.setId(count.incrementAndGet());

        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tableDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();
        table.setId(count.incrementAndGet());

        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();
        table.setId(count.incrementAndGet());

        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTableWithPatch() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        int databaseSizeBeforeUpdate = tableRepository.findAll().size();

        // Update the table using partial update
        Table partialUpdatedTable = new Table();
        partialUpdatedTable.setId(table.getId());

        restTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTable.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTable))
            )
            .andExpect(status().isOk());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
        Table testTable = tableList.get(tableList.size() - 1);
        assertThat(testTable.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateTableWithPatch() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        int databaseSizeBeforeUpdate = tableRepository.findAll().size();

        // Update the table using partial update
        Table partialUpdatedTable = new Table();
        partialUpdatedTable.setId(table.getId());

        partialUpdatedTable.number(UPDATED_NUMBER);

        restTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTable.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTable))
            )
            .andExpect(status().isOk());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
        Table testTable = tableList.get(tableList.size() - 1);
        assertThat(testTable.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();
        table.setId(count.incrementAndGet());

        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tableDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();
        table.setId(count.incrementAndGet());

        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTable() throws Exception {
        int databaseSizeBeforeUpdate = tableRepository.findAll().size();
        table.setId(count.incrementAndGet());

        // Create the Table
        TableDTO tableDTO = tableMapper.toDto(table);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Table in the database
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTable() throws Exception {
        // Initialize the database
        tableRepository.saveAndFlush(table);

        int databaseSizeBeforeDelete = tableRepository.findAll().size();

        // Delete the table
        restTableMockMvc
            .perform(delete(ENTITY_API_URL_ID, table.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Table> tableList = tableRepository.findAll();
        assertThat(tableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
