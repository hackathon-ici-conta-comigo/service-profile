package org.contacomigo.profile.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.contacomigo.profile.domain.ProfileDetails;
import org.contacomigo.profile.service.ProfileDetailsService;
import org.contacomigo.profile.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ProfileDetails.
 */
@RestController
@RequestMapping("/api")
public class ProfileDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProfileDetailsResource.class);
    private static final String ENTITY_NAME = "profileDetails";

    private final ProfileDetailsService profileDetailsService;

    public ProfileDetailsResource(ProfileDetailsService profileDetailsService) {
        this.profileDetailsService = profileDetailsService;
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
        ProfileDetails result = profileDetailsService.save(profileDetails);
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
        ProfileDetails result = profileDetailsService.save(profileDetails);
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
        List<ProfileDetails> profileDetails = profileDetailsService.findAll();
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
        ProfileDetails profileDetails = profileDetailsService.findOne(id);
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
        profileDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/profile-detail/search")
    @Timed
    public List<ProfileDetails> findByAddress(@RequestParam("address") String address) {
        log.debug("REST search profile detail by address");
        return profileDetailsService.findByAddress(address);
    }
}
