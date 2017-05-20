package org.contacomigo.profile.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.contacomigo.profile.ProfileApp;
import org.contacomigo.profile.config.SecurityBeanOverrideConfiguration;
import org.contacomigo.profile.domain.ProfileDetails;
import org.contacomigo.profile.service.ProfileDetailsService;
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

/**
 * Test class for the ProfileDetailsResource REST controller.
 *
 * @see ProfileDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProfileApp.class, SecurityBeanOverrideConfiguration.class})
public class ProfileDetailsResourceIntTest {

    private static final String DEFAULT_PROFILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_ID = "BBBBBBBBBB";

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProfileDetailsMockMvc;

    private ProfileDetails profileDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileDetailsResource profileDetailsResource = new ProfileDetailsResource(profileDetailsService);
        this.restProfileDetailsMockMvc = MockMvcBuilders.standaloneSetup(profileDetailsResource)
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
    public static ProfileDetails createEntity() {
        ProfileDetails profileDetails = new ProfileDetails()
            .profileId(DEFAULT_PROFILE_ID)
            .detailId(DEFAULT_DETAIL_ID);
        return profileDetails;
    }

    @Before
    public void initTest() {
    	profileDetailsService.deleteAll();
        profileDetails = createEntity();
    }

    @Test
    public void createProfileDetails() throws Exception {
        int databaseSizeBeforeCreate = profileDetailsService.findAll().size();

        // Create the ProfileDetails
        restProfileDetailsMockMvc.perform(post("/api/profile-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDetails)))
            .andExpect(status().isCreated());

        // Validate the ProfileDetails in the database
        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileDetails testProfileDetails = profileDetailsList.get(profileDetailsList.size() - 1);
        assertThat(testProfileDetails.getProfileId()).isEqualTo(DEFAULT_PROFILE_ID);
        assertThat(testProfileDetails.getDetailId()).isEqualTo(DEFAULT_DETAIL_ID);
    }

    @Test
    public void createProfileDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileDetailsService.findAll().size();

        // Create the ProfileDetails with an existing ID
        profileDetails.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileDetailsMockMvc.perform(post("/api/profile-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkProfileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileDetailsService.findAll().size();
        // set the field null
        profileDetails.setProfileId(null);

        // Create the ProfileDetails, which fails.

        restProfileDetailsMockMvc.perform(post("/api/profile-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDetails)))
            .andExpect(status().isBadRequest());

        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDetailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileDetailsService.findAll().size();
        // set the field null
        profileDetails.setDetailId(null);

        // Create the ProfileDetails, which fails.

        restProfileDetailsMockMvc.perform(post("/api/profile-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDetails)))
            .andExpect(status().isBadRequest());

        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProfileDetails() throws Exception {
        // Initialize the database
    	profileDetailsService.save(profileDetails);

        // Get all the profileDetailsList
        restProfileDetailsMockMvc.perform(get("/api/profile-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileDetails.getId())))
            .andExpect(jsonPath("$.[*].profileId").value(hasItem(DEFAULT_PROFILE_ID.toString())))
            .andExpect(jsonPath("$.[*].detailId").value(hasItem(DEFAULT_DETAIL_ID.toString())));
    }

    @Test
    public void getProfileDetails() throws Exception {
        // Initialize the database
    	profileDetailsService.save(profileDetails);

        // Get the profileDetails
        restProfileDetailsMockMvc.perform(get("/api/profile-details/{id}", profileDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileDetails.getId()))
            .andExpect(jsonPath("$.profileId").value(DEFAULT_PROFILE_ID.toString()))
            .andExpect(jsonPath("$.detailId").value(DEFAULT_DETAIL_ID.toString()));
    }

    @Test
    public void getNonExistingProfileDetails() throws Exception {
        // Get the profileDetails
        restProfileDetailsMockMvc.perform(get("/api/profile-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProfileDetails() throws Exception {
        // Initialize the database
    	profileDetailsService.save(profileDetails);
        int databaseSizeBeforeUpdate = profileDetailsService.findAll().size();

        // Update the profileDetails
        ProfileDetails updatedProfileDetails = profileDetailsService.findOne(profileDetails.getId());
        updatedProfileDetails
            .profileId(UPDATED_PROFILE_ID)
            .detailId(UPDATED_DETAIL_ID);

        restProfileDetailsMockMvc.perform(put("/api/profile-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfileDetails)))
            .andExpect(status().isOk());

        // Validate the ProfileDetails in the database
        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProfileDetails testProfileDetails = profileDetailsList.get(profileDetailsList.size() - 1);
        assertThat(testProfileDetails.getProfileId()).isEqualTo(UPDATED_PROFILE_ID);
        assertThat(testProfileDetails.getDetailId()).isEqualTo(UPDATED_DETAIL_ID);
    }

    @Test
    public void updateNonExistingProfileDetails() throws Exception {
        int databaseSizeBeforeUpdate = profileDetailsService.findAll().size();

        // Create the ProfileDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileDetailsMockMvc.perform(put("/api/profile-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDetails)))
            .andExpect(status().isCreated());

        // Validate the ProfileDetails in the database
        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProfileDetails() throws Exception {
        // Initialize the database
    	profileDetailsService.save(profileDetails);
        int databaseSizeBeforeDelete = profileDetailsService.findAll().size();

        // Get the profileDetails
        restProfileDetailsMockMvc.perform(delete("/api/profile-details/{id}", profileDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfileDetails> profileDetailsList = profileDetailsService.findAll();
        assertThat(profileDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDetails.class);
    }
}
