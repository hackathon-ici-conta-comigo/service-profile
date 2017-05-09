package org.contacomigo.profile.web.rest;

import org.contacomigo.profile.ProfileApp;

import org.contacomigo.profile.config.SecurityBeanOverrideConfiguration;

import org.contacomigo.profile.domain.Details;
import org.contacomigo.profile.repository.DetailsRepository;
import org.contacomigo.profile.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.contacomigo.profile.domain.enumeration.DetailType;
/**
 * Test class for the DetailsResource REST controller.
 *
 * @see DetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProfileApp.class, SecurityBeanOverrideConfiguration.class})
public class DetailsResourceIntTest {

    private static final String DEFAULT_DETAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_ID = "BBBBBBBBBB";

    private static final DetailType DEFAULT_TYPE = DetailType.INTEREST;
    private static final DetailType UPDATED_TYPE = DetailType.HABILITY;

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDetailsMockMvc;

    private Details details;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DetailsResource detailsResource = new DetailsResource(detailsRepository);
        this.restDetailsMockMvc = MockMvcBuilders.standaloneSetup(detailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Details createEntity() {
        Details details = new Details()
            .detailId(DEFAULT_DETAIL_ID)
            .type(DEFAULT_TYPE);
        return details;
    }

    @Before
    public void initTest() {
        detailsRepository.deleteAll();
        details = createEntity();
    }

    @Test
    public void createDetails() throws Exception {
        int databaseSizeBeforeCreate = detailsRepository.findAll().size();

        // Create the Details
        restDetailsMockMvc.perform(post("/api/details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(details)))
            .andExpect(status().isCreated());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeCreate + 1);
        Details testDetails = detailsList.get(detailsList.size() - 1);
        assertThat(testDetails.getDetailId()).isEqualTo(DEFAULT_DETAIL_ID);
        assertThat(testDetails.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailsRepository.findAll().size();

        // Create the Details with an existing ID
        details.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsMockMvc.perform(post("/api/details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(details)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDetailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsRepository.findAll().size();
        // set the field null
        details.setDetailId(null);

        // Create the Details, which fails.

        restDetailsMockMvc.perform(post("/api/details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(details)))
            .andExpect(status().isBadRequest());

        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsRepository.findAll().size();
        // set the field null
        details.setType(null);

        // Create the Details, which fails.

        restDetailsMockMvc.perform(post("/api/details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(details)))
            .andExpect(status().isBadRequest());

        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDetails() throws Exception {
        // Initialize the database
        detailsRepository.save(details);

        // Get all the detailsList
        restDetailsMockMvc.perform(get("/api/details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(details.getId())))
            .andExpect(jsonPath("$.[*].detailId").value(hasItem(DEFAULT_DETAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    public void getDetails() throws Exception {
        // Initialize the database
        detailsRepository.save(details);

        // Get the details
        restDetailsMockMvc.perform(get("/api/details/{id}", details.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(details.getId()))
            .andExpect(jsonPath("$.detailId").value(DEFAULT_DETAIL_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingDetails() throws Exception {
        // Get the details
        restDetailsMockMvc.perform(get("/api/details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDetails() throws Exception {
        // Initialize the database
        detailsRepository.save(details);
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

        // Update the details
        Details updatedDetails = detailsRepository.findOne(details.getId());
        updatedDetails
            .detailId(UPDATED_DETAIL_ID)
            .type(UPDATED_TYPE);

        restDetailsMockMvc.perform(put("/api/details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetails)))
            .andExpect(status().isOk());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate);
        Details testDetails = detailsList.get(detailsList.size() - 1);
        assertThat(testDetails.getDetailId()).isEqualTo(UPDATED_DETAIL_ID);
        assertThat(testDetails.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingDetails() throws Exception {
        int databaseSizeBeforeUpdate = detailsRepository.findAll().size();

        // Create the Details

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailsMockMvc.perform(put("/api/details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(details)))
            .andExpect(status().isCreated());

        // Validate the Details in the database
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDetails() throws Exception {
        // Initialize the database
        detailsRepository.save(details);
        int databaseSizeBeforeDelete = detailsRepository.findAll().size();

        // Get the details
        restDetailsMockMvc.perform(delete("/api/details/{id}", details.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Details> detailsList = detailsRepository.findAll();
        assertThat(detailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Details.class);
    }
}
