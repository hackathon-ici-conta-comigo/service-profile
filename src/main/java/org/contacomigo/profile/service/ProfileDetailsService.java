package org.contacomigo.profile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.contacomigo.profile.domain.Address;
import org.contacomigo.profile.domain.ProfileDetails;
import org.contacomigo.profile.repository.ProfileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Profile.
 */
@Service
public class ProfileDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProfileDetailsService.class);

    private final AddressService addressService;
    private final ProfileDetailsRepository profileDetailsRepository;

    public ProfileDetailsService(ProfileDetailsRepository profileDetailsRepository, AddressService addressService) {
        this.profileDetailsRepository = profileDetailsRepository;
        this.addressService = addressService;
    }

    /**
     * Save a profile.
     *
     * @param profileDetails the entity to save
     * @return the persisted entity
     */
    public ProfileDetails save(ProfileDetails profileDetails) {
        log.debug("Request to save Profile : {}", profileDetails);
        return profileDetailsRepository.save(profileDetails);
    }

    public List<ProfileDetails> save(List<ProfileDetails> profileDetails) {
        log.debug("Request to save Profile : {}", profileDetails);
        return profileDetailsRepository.save(profileDetails);
    }

    /**
     *  Get all the profiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProfileDetails> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileDetailsRepository.findAll(pageable);
    }

    public List<ProfileDetails> findAll() {
        log.debug("Request to get all Profiles");
        return profileDetailsRepository.findAll();
    }

    /**
     *  Get one profile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProfileDetails findOne(String id) {
        log.debug("Request to get Profile : {}", id);
        return profileDetailsRepository.findOne(id);
    }

    /**
     *  Delete the  profile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Profile : {}", id);
        profileDetailsRepository.delete(id);
    }

    public void deleteAll() {
        profileDetailsRepository.deleteAll();
    }

    public List<ProfileDetails> findByAddress(String address) {
    	final List<ProfileDetails> result = new ArrayList<ProfileDetails>();
    	final TextCriteria criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).matchingAny(address.split(" "));
    	final List<Address> addresses = addressService.findBy(criteria);

    	if (addresses != null) {
    		List<String> idList = addresses.stream().map(Address::getProfileId).collect(Collectors.toList());
    		profileDetailsRepository.findByProfileIdIn(idList).iterator().forEachRemaining(result::add);
    	}

        return result;
    }
}
