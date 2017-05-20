package org.contacomigo.profile.repository;

import java.util.List;

import org.contacomigo.profile.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Address entity.
 */
public interface AddressRepository extends MongoRepository<Address,String> {

	Page<Address> findBy(TextCriteria criteria, Pageable page);

	List<Address> findBy(TextCriteria criteria);
}
