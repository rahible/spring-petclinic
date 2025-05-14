package org.springframework.samples.petclinic.vet;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface VetRepository extends Repository<Vet, Integer> {

	@Transactional(readOnly = true)
	Collection<Vet> findAll() throws DataAccessException;

	@Transactional(readOnly = true)
	Page<Vet> findAll(Pageable pageable) throws DataAccessException;

	@Transactional
	Vet save(Vet vet) throws DataAccessException;

}
