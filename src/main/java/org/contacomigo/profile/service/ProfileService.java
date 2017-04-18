package org.contacomigo.profile.service;

import org.contacomigo.profile.domain.Profile;
import org.contacomigo.profile.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Profile.
 */
@Service
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);
    
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Save a profile.
     *
     * @param profile the entity to save
     * @return the persisted entity
     */
    public Profile save(Profile profile) {
        log.debug("Request to save Profile : {}", profile);
        Profile result = profileRepository.save(profile);
        return result;
    }

    /**
     *  Get all the profiles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Profile> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        Page<Profile> result = profileRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one profile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Profile findOne(String id) {
        log.debug("Request to get Profile : {}", id);
        Profile profile = profileRepository.findOne(id);
        return profile;
    }

    /**
     *  Delete the  profile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.delete(id);
    }
}
