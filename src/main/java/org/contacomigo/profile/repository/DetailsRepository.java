package org.contacomigo.profile.repository;

import org.contacomigo.profile.domain.Details;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Details entity.
 */
@SuppressWarnings("unused")
public interface DetailsRepository extends MongoRepository<Details,String> {

}
