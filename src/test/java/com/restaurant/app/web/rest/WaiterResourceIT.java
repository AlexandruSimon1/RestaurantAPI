package com.restaurant.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.restaurant.app.IntegrationTest;
import com.restaurant.app.domain.Waiter;
import com.restaurant.app.repository.WaiterRepository;
import com.restaurant.app.service.criteria.WaiterCriteria;
import com.restaurant.app.service.dto.WaiterDTO;
import com.restaurant.app.service.mapper.WaiterMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link WaiterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WaiterResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;
    private static final Long SMALLER_PHONE_NUMBER = 1L - 1L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/waiters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WaiterRepository waiterRepository;

    @Autowired
    private WaiterMapper waiterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWaiterMockMvc;

    private Waiter waiter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waiter createEntity(EntityManager em) {
        Waiter waiter = new Waiter()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL);
        return waiter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Waiter createUpdatedEntity(EntityManager em) {
        Waiter waiter = new Waiter()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);
        return waiter;
    }

    @BeforeEach
    public void initTest() {
        waiter = createEntity(em);
    }

    @Test
    @Transactional
    void createWaiter() throws Exception {
        int databaseSizeBeforeCreate = waiterRepository.findAll().size();
        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);
        restWaiterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeCreate + 1);
        Waiter testWaiter = waiterList.get(waiterList.size() - 1);
        assertThat(testWaiter.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testWaiter.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testWaiter.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testWaiter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWaiter.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testWaiter.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createWaiterWithExistingId() throws Exception {
        // Create the Waiter with an existing ID
        waiter.setId(1L);
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        int databaseSizeBeforeCreate = waiterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaiterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWaiters() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList
        restWaiterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getWaiter() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get the waiter
        restWaiterMockMvc
            .perform(get(ENTITY_API_URL_ID, waiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(waiter.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getWaitersByIdFiltering() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        Long id = waiter.getId();

        defaultWaiterShouldBeFound("id.equals=" + id);
        defaultWaiterShouldNotBeFound("id.notEquals=" + id);

        defaultWaiterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWaiterShouldNotBeFound("id.greaterThan=" + id);

        defaultWaiterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWaiterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWaitersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where firstName equals to DEFAULT_FIRST_NAME
        defaultWaiterShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the waiterList where firstName equals to UPDATED_FIRST_NAME
        defaultWaiterShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where firstName not equals to DEFAULT_FIRST_NAME
        defaultWaiterShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the waiterList where firstName not equals to UPDATED_FIRST_NAME
        defaultWaiterShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultWaiterShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the waiterList where firstName equals to UPDATED_FIRST_NAME
        defaultWaiterShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where firstName is not null
        defaultWaiterShouldBeFound("firstName.specified=true");

        // Get all the waiterList where firstName is null
        defaultWaiterShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllWaitersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where firstName contains DEFAULT_FIRST_NAME
        defaultWaiterShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the waiterList where firstName contains UPDATED_FIRST_NAME
        defaultWaiterShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where firstName does not contain DEFAULT_FIRST_NAME
        defaultWaiterShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the waiterList where firstName does not contain UPDATED_FIRST_NAME
        defaultWaiterShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where lastName equals to DEFAULT_LAST_NAME
        defaultWaiterShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the waiterList where lastName equals to UPDATED_LAST_NAME
        defaultWaiterShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where lastName not equals to DEFAULT_LAST_NAME
        defaultWaiterShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the waiterList where lastName not equals to UPDATED_LAST_NAME
        defaultWaiterShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultWaiterShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the waiterList where lastName equals to UPDATED_LAST_NAME
        defaultWaiterShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where lastName is not null
        defaultWaiterShouldBeFound("lastName.specified=true");

        // Get all the waiterList where lastName is null
        defaultWaiterShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllWaitersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where lastName contains DEFAULT_LAST_NAME
        defaultWaiterShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the waiterList where lastName contains UPDATED_LAST_NAME
        defaultWaiterShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where lastName does not contain DEFAULT_LAST_NAME
        defaultWaiterShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the waiterList where lastName does not contain UPDATED_LAST_NAME
        defaultWaiterShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth is not null
        defaultWaiterShouldBeFound("dateOfBirth.specified=true");

        // Get all the waiterList where dateOfBirth is null
        defaultWaiterShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultWaiterShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the waiterList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultWaiterShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllWaitersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where address equals to DEFAULT_ADDRESS
        defaultWaiterShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the waiterList where address equals to UPDATED_ADDRESS
        defaultWaiterShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWaitersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where address not equals to DEFAULT_ADDRESS
        defaultWaiterShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the waiterList where address not equals to UPDATED_ADDRESS
        defaultWaiterShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWaitersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultWaiterShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the waiterList where address equals to UPDATED_ADDRESS
        defaultWaiterShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWaitersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where address is not null
        defaultWaiterShouldBeFound("address.specified=true");

        // Get all the waiterList where address is null
        defaultWaiterShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllWaitersByAddressContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where address contains DEFAULT_ADDRESS
        defaultWaiterShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the waiterList where address contains UPDATED_ADDRESS
        defaultWaiterShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWaitersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where address does not contain DEFAULT_ADDRESS
        defaultWaiterShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the waiterList where address does not contain UPDATED_ADDRESS
        defaultWaiterShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber is not null
        defaultWaiterShouldBeFound("phoneNumber.specified=true");

        // Get all the waiterList where phoneNumber is null
        defaultWaiterShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber is greater than or equal to DEFAULT_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.greaterThanOrEqual=" + DEFAULT_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber is greater than or equal to UPDATED_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.greaterThanOrEqual=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber is less than or equal to DEFAULT_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.lessThanOrEqual=" + DEFAULT_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber is less than or equal to SMALLER_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.lessThanOrEqual=" + SMALLER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber is less than DEFAULT_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.lessThan=" + DEFAULT_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber is less than UPDATED_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.lessThan=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByPhoneNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where phoneNumber is greater than DEFAULT_PHONE_NUMBER
        defaultWaiterShouldNotBeFound("phoneNumber.greaterThan=" + DEFAULT_PHONE_NUMBER);

        // Get all the waiterList where phoneNumber is greater than SMALLER_PHONE_NUMBER
        defaultWaiterShouldBeFound("phoneNumber.greaterThan=" + SMALLER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllWaitersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where email equals to DEFAULT_EMAIL
        defaultWaiterShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the waiterList where email equals to UPDATED_EMAIL
        defaultWaiterShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllWaitersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where email not equals to DEFAULT_EMAIL
        defaultWaiterShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the waiterList where email not equals to UPDATED_EMAIL
        defaultWaiterShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllWaitersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultWaiterShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the waiterList where email equals to UPDATED_EMAIL
        defaultWaiterShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllWaitersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where email is not null
        defaultWaiterShouldBeFound("email.specified=true");

        // Get all the waiterList where email is null
        defaultWaiterShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllWaitersByEmailContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where email contains DEFAULT_EMAIL
        defaultWaiterShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the waiterList where email contains UPDATED_EMAIL
        defaultWaiterShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllWaitersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        // Get all the waiterList where email does not contain DEFAULT_EMAIL
        defaultWaiterShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the waiterList where email does not contain UPDATED_EMAIL
        defaultWaiterShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWaiterShouldBeFound(String filter) throws Exception {
        restWaiterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restWaiterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWaiterShouldNotBeFound(String filter) throws Exception {
        restWaiterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWaiterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWaiter() throws Exception {
        // Get the waiter
        restWaiterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWaiter() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();

        // Update the waiter
        Waiter updatedWaiter = waiterRepository.findById(waiter.getId()).get();
        // Disconnect from session so that the updates on updatedWaiter are not directly saved in db
        em.detach(updatedWaiter);
        updatedWaiter
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);
        WaiterDTO waiterDTO = waiterMapper.toDto(updatedWaiter);

        restWaiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, waiterDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isOk());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
        Waiter testWaiter = waiterList.get(waiterList.size() - 1);
        assertThat(testWaiter.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testWaiter.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testWaiter.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testWaiter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWaiter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testWaiter.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingWaiter() throws Exception {
        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();
        waiter.setId(count.incrementAndGet());

        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, waiterDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWaiter() throws Exception {
        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();
        waiter.setId(count.incrementAndGet());

        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWaiter() throws Exception {
        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();
        waiter.setId(count.incrementAndGet());

        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaiterMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWaiterWithPatch() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();

        // Update the waiter using partial update
        Waiter partialUpdatedWaiter = new Waiter();
        partialUpdatedWaiter.setId(waiter.getId());

        partialUpdatedWaiter
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);

        restWaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaiter.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWaiter))
            )
            .andExpect(status().isOk());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
        Waiter testWaiter = waiterList.get(waiterList.size() - 1);
        assertThat(testWaiter.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testWaiter.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testWaiter.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testWaiter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWaiter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testWaiter.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateWaiterWithPatch() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();

        // Update the waiter using partial update
        Waiter partialUpdatedWaiter = new Waiter();
        partialUpdatedWaiter.setId(waiter.getId());

        partialUpdatedWaiter
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);

        restWaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWaiter.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWaiter))
            )
            .andExpect(status().isOk());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
        Waiter testWaiter = waiterList.get(waiterList.size() - 1);
        assertThat(testWaiter.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testWaiter.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testWaiter.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testWaiter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWaiter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testWaiter.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingWaiter() throws Exception {
        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();
        waiter.setId(count.incrementAndGet());

        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, waiterDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWaiter() throws Exception {
        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();
        waiter.setId(count.incrementAndGet());

        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWaiter() throws Exception {
        int databaseSizeBeforeUpdate = waiterRepository.findAll().size();
        waiter.setId(count.incrementAndGet());

        // Create the Waiter
        WaiterDTO waiterDTO = waiterMapper.toDto(waiter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWaiterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(waiterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Waiter in the database
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWaiter() throws Exception {
        // Initialize the database
        waiterRepository.saveAndFlush(waiter);

        int databaseSizeBeforeDelete = waiterRepository.findAll().size();

        // Delete the waiter
        restWaiterMockMvc
            .perform(delete(ENTITY_API_URL_ID, waiter.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Waiter> waiterList = waiterRepository.findAll();
        assertThat(waiterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
