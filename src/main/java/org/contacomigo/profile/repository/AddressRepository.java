package org.contacomigo.profile.repository;

import org.contacomigo.profile.domain.Address;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Address entity.
 */
@SuppressWarnings("unused")
public interface AddressRepository extends MongoRepository<Address,String> {

}
