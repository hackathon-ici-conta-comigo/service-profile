package org.contacomigo.profile.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.contacomigo.profile.domain.ProfileDetails;

import org.contacomigo.profile.repository.ProfileDetailsRepository;
import org.contacomigo.profile.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProfileDetails.
 */
@RestController
@RequestMapping("/api")
public class ProfileDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProfileDetailsResource.class);

    private static final String ENTITY_NAME = "profileDetails";
        
    private final ProfileDetailsRepository profileDetailsRepository;

    public ProfileDetailsResource(ProfileDetailsRepository profileDetailsRepository) {
        this.profileDetailsRepository = profileDetailsRepository;
    }

    /**
     * POST  /profile-details : Create a new profileDetails.
     *
     * @param profileDetails the profileDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileDetails, or with status 400 (Bad Request) if the profileDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-details")
    @Timed
    public ResponseEntity<ProfileDetails> createProfileDetails(@Valid @RequestBody ProfileDetails profileDetails) throws URISyntaxException {
        log.debug("REST request to save ProfileDetails : {}", profileDetails);
        if (profileDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profileDetails cannot already have an ID")).body(null);
        }
        ProfileDetails result = profileDetailsRepository.save(profileDetails);
        return ResponseEntity.created(new URI("/api/profile-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profile-details : Updates an existing profileDetails.
     *
     * @param profileDetails the profileDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileDetails,
     * or with status 400 (Bad Request) if the profileDetails is not valid,
     * or with status 500 (Internal Server Error) if the profileDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-details")
    @Timed
    public ResponseEntity<ProfileDetails> updateProfileDetails(@Valid @RequestBody ProfileDetails profileDetails) throws URISyntaxException {
        log.debug("REST request to update ProfileDetails : {}", profileDetails);
        if (profileDetails.getId() == null) {
            return createProfileDetails(profileDetails);
        }
        ProfileDetails result = profileDetailsRepository.save(profileDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profile-details : get all the profileDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of profileDetails in body
     */
    @GetMapping("/profile-details")
    @Timed
    public List<ProfileDetails> getAllProfileDetails() {
        log.debug("REST request to get all ProfileDetails");
        List<ProfileDetails> profileDetails = profileDetailsRepository.findAll();
        return profileDetails;
    }

    /**
     * GET  /profile-details/:id : get the "id" profileDetails.
     *
     * @param id the id of the profileDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileDetails, or with status 404 (Not Found)
     */
    @GetMapping("/profile-details/{id}")
    @Timed
    public ResponseEntity<ProfileDetails> getProfileDetails(@PathVariable String id) {
        log.debug("REST request to get ProfileDetails : {}", id);
        ProfileDetails profileDetails = profileDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileDetails));
    }

    /**
     * DELETE  /profile-details/:id : delete the "id" profileDetails.
     *
     * @param id the id of the profileDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profile-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfileDetails(@PathVariable String id) {
        log.debug("REST request to delete ProfileDetails : {}", id);
        profileDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
