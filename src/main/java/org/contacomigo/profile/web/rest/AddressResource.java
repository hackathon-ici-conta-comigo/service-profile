package org.contacomigo.profile.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.contacomigo.profile.domain.Address;
import org.contacomigo.profile.service.AddressService;
import org.contacomigo.profile.web.rest.util.HeaderUtil;
import org.contacomigo.profile.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";
        
    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * POST  /addresses : Create a new address.
     *
     * @param address the address to create
     * @return the ResponseEntity with status 201 (Created) and with body the new address, or with status 400 (Bad Request) if the address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addresses")
    @Timed
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to save Address : {}", address);
        if (address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new address cannot already have an ID")).body(null);
        }
        Address result = addressService.save(address);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresses : Updates an existing address.
     *
     * @param address the address to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated address,
     * or with status 400 (Bad Request) if the address is not valid,
     * or with status 500 (Internal Server Error) if the address couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addresses")
    @Timed
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to update Address : {}", address);
        if (address.getId() == null) {
            return createAddress(address);
        }
        Address result = addressService.save(address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    @Timed
    public ResponseEntity<List<Address>> getAllAddresses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Addresses");
        Page<Address> page = addressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the address to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the address, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Address> getAddress(@PathVariable String id) {
        log.debug("REST request to get Address : {}", id);
        Address address = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(address));
    }

    /**
     * DELETE  /addresses/:id : delete the "id" address.
     *
     * @param id the id of the address to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
