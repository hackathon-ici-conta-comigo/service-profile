package org.contacomigo.profile.service;

import java.util.List;

import org.contacomigo.profile.domain.Address;
import org.contacomigo.profile.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Address.
 */
@Service
public class AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Save a address.
     *
     * @param address the entity to save
     * @return the persisted entity
     */
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        Address result = addressRepository.save(address);
        return result;
    }

    public List<Address> save(List<Address> addresses) {
        return addressRepository.save(addresses);
    }

    /**
     *  Get all the addresses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Address> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        Page<Address> result = addressRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one address by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Address findOne(String id) {
        log.debug("Request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        return address;
    }

    /**
     *  Delete the  address by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
    }

    public void deleteAll() {
    	addressRepository.deleteAll();
    }

	public List<Address> findBy(TextCriteria criteria) {
		return addressRepository.findBy(criteria);
	}

	public Page<Address> findBy(TextCriteria criteria, Pageable page) {
		return addressRepository.findBy(criteria, page);
	}
}
