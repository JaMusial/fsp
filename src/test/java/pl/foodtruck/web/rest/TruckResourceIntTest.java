package pl.foodtruck.web.rest;

import pl.foodtruck.FoodtruckApp;

import pl.foodtruck.domain.Truck;
import pl.foodtruck.domain.User;
import pl.foodtruck.repository.TruckRepository;
import pl.foodtruck.service.TruckService;
import pl.foodtruck.service.dto.TruckDTO;
import pl.foodtruck.service.mapper.TruckMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TruckResource REST controller.
 *
 * @see TruckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodtruckApp.class)
public class TruckResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Inject
    private TruckRepository truckRepository;

    @Inject
    private TruckMapper truckMapper;

    @Inject
    private TruckService truckService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTruckMockMvc;

    private Truck truck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TruckResource truckResource = new TruckResource();
        ReflectionTestUtils.setField(truckResource, "truckService", truckService);
        this.restTruckMockMvc = MockMvcBuilders.standaloneSetup(truckResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Truck createEntity(EntityManager em) {
        Truck truck = new Truck()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .tel(DEFAULT_TEL);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        truck.setUser(user);
        return truck;
    }

    @Before
    public void initTest() {
        truck = createEntity(em);
    }

    @Test
    @Transactional
    public void createTruck() throws Exception {
        int databaseSizeBeforeCreate = truckRepository.findAll().size();

        // Create the Truck
        TruckDTO truckDTO = truckMapper.truckToTruckDTO(truck);

        restTruckMockMvc.perform(post("/api/trucks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
                .andExpect(status().isCreated());

        // Validate the Truck in the database
        List<Truck> trucks = truckRepository.findAll();
        assertThat(trucks).hasSize(databaseSizeBeforeCreate + 1);
        Truck testTruck = trucks.get(trucks.size() - 1);
        assertThat(testTruck.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTruck.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTruck.getTel()).isEqualTo(DEFAULT_TEL);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setName(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.truckToTruckDTO(truck);

        restTruckMockMvc.perform(post("/api/trucks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
                .andExpect(status().isBadRequest());

        List<Truck> trucks = truckRepository.findAll();
        assertThat(trucks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setEmail(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.truckToTruckDTO(truck);

        restTruckMockMvc.perform(post("/api/trucks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
                .andExpect(status().isBadRequest());

        List<Truck> trucks = truckRepository.findAll();
        assertThat(trucks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setTel(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.truckToTruckDTO(truck);

        restTruckMockMvc.perform(post("/api/trucks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
                .andExpect(status().isBadRequest());

        List<Truck> trucks = truckRepository.findAll();
        assertThat(trucks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrucks() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get all the trucks
        restTruckMockMvc.perform(get("/api/trucks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    @Transactional
    public void getTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);

        // Get the truck
        restTruckMockMvc.perform(get("/api/trucks/{id}", truck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(truck.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTruck() throws Exception {
        // Get the truck
        restTruckMockMvc.perform(get("/api/trucks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

        // Update the truck
        Truck updatedTruck = truckRepository.findOne(truck.getId());
        updatedTruck
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .tel(UPDATED_TEL);
        TruckDTO truckDTO = truckMapper.truckToTruckDTO(updatedTruck);

        restTruckMockMvc.perform(put("/api/trucks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(truckDTO)))
                .andExpect(status().isOk());

        // Validate the Truck in the database
        List<Truck> trucks = truckRepository.findAll();
        assertThat(trucks).hasSize(databaseSizeBeforeUpdate);
        Truck testTruck = trucks.get(trucks.size() - 1);
        assertThat(testTruck.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTruck.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTruck.getTel()).isEqualTo(UPDATED_TEL);
    }

    @Test
    @Transactional
    public void deleteTruck() throws Exception {
        // Initialize the database
        truckRepository.saveAndFlush(truck);
        int databaseSizeBeforeDelete = truckRepository.findAll().size();

        // Get the truck
        restTruckMockMvc.perform(delete("/api/trucks/{id}", truck.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Truck> trucks = truckRepository.findAll();
        assertThat(trucks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
