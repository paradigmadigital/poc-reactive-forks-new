package com.paradigmadigital.poc.reactive.idnames;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IdNameService {

    Flux<IdName> getAll();

    Mono<IdName> getById(Long id);

    Mono<IdName> insert(IdName payload);

    Mono<IdName> update(IdName payload);

    Mono<Void> deleteById(Long id);

}
