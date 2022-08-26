package com.paradigmadigital.poc.reactive.idnames;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IdNameRepository extends ReactiveCrudRepository<IdName, Long> {
}
