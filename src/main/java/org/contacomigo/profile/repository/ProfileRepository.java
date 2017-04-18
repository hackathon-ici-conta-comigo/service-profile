package org.contacomigo.profile.repository;

import org.contacomigo.profile.domain.Profile;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Profile entity.
 */
@SuppressWarnings("unused")
public interface ProfileRepository extends MongoRepository<Profile,String> {

}
