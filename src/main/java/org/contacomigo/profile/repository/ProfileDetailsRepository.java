package org.contacomigo.profile.repository;

import java.util.List;

import org.contacomigo.profile.domain.ProfileDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProfileDetails entity.
 */
public interface ProfileDetailsRepository extends MongoRepository<ProfileDetails,String> {

	List<ProfileDetails> findByProfileIdIn(List<String> ids);

}
