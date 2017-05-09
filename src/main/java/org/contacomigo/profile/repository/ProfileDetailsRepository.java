package org.contacomigo.profile.repository;

import org.contacomigo.profile.domain.ProfileDetails;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProfileDetails entity.
 */
@SuppressWarnings("unused")
public interface ProfileDetailsRepository extends MongoRepository<ProfileDetails,String> {

}
