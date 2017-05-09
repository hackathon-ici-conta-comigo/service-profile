package org.contacomigo.profile.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.contacomigo.profile.domain.Details;

import org.contacomigo.profile.repository.DetailsRepository;
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
 * REST controller for managing Details.
 */
@RestController
@RequestMapping("/api")
public class DetailsResource {

    private final Logger log = LoggerFactory.getLogger(DetailsResource.class);

    private static final String ENTITY_NAME = "details";
        
    private final DetailsRepository detailsRepository;

    public DetailsResource(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    /**
     * POST  /details : Create a new details.
     *
     * @param details the details to create
     * @return the ResponseEntity with status 201 (Created) and with body the new details, or with status 400 (Bad Request) if the details has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/details")
    @Timed
    public ResponseEntity<Details> createDetails(@Valid @RequestBody Details details) throws URISyntaxException {
        log.debug("REST request to save Details : {}", details);
        if (details.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new details cannot already have an ID")).body(null);
        }
        Details result = detailsRepository.save(details);
        return ResponseEntity.created(new URI("/api/details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /details : Updates an existing details.
     *
     * @param details the details to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated details,
     * or with status 400 (Bad Request) if the details is not valid,
     * or with status 500 (Internal Server Error) if the details couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/details")
    @Timed
    public ResponseEntity<Details> updateDetails(@Valid @RequestBody Details details) throws URISyntaxException {
        log.debug("REST request to update Details : {}", details);
        if (details.getId() == null) {
            return createDetails(details);
        }
        Details result = detailsRepository.save(details);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, details.getId().toString()))
            .body(result);
    }

    /**
     * GET  /details : get all the details.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of details in body
     */
    @GetMapping("/details")
    @Timed
    public List<Details> getAllDetails() {
        log.debug("REST request to get all Details");
        List<Details> details = detailsRepository.findAll();
        return details;
    }

    /**
     * GET  /details/:id : get the "id" details.
     *
     * @param id the id of the details to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the details, or with status 404 (Not Found)
     */
    @GetMapping("/details/{id}")
    @Timed
    public ResponseEntity<Details> getDetails(@PathVariable String id) {
        log.debug("REST request to get Details : {}", id);
        Details details = detailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(details));
    }

    /**
     * DELETE  /details/:id : delete the "id" details.
     *
     * @param id the id of the details to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/details/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetails(@PathVariable String id) {
        log.debug("REST request to delete Details : {}", id);
        detailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
