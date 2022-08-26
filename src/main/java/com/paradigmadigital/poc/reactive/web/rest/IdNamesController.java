package com.paradigmadigital.poc.reactive.web.rest;

import com.paradigmadigital.poc.reactive.idnames.IdName;
import com.paradigmadigital.poc.reactive.idnames.IdNameService;
import com.paradigmadigital.poc.reactive.exception.ServiceException;
import com.paradigmadigital.poc.reactive.web.bind.CrudRestApi;
import com.paradigmadigital.poc.reactive.web.bind.PreviousRestApi;
import com.paradigmadigital.poc.reactive.web.model.IdNameDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "idName")
@RestController
@RequestMapping("/idNames")
class IdNamesController implements CrudRestApi<IdNameDTO, Long>, PreviousRestApi {

    private final IdNameService idNameService;

    @Override
    public Flux<IdNameDTO> getAll() {
        return idNameService.getAll()
                .map(IdNameDTO::from);
    }

    @Override
    public Mono<IdNameDTO> getById(Long id) {
        return idNameService.getById(id)
                .switchIfEmpty(Mono.error(ServiceException.notFound(id)))
                .map(IdNameDTO::from);
    }

    @Override
    public Mono<IdNameDTO> insertOne(Mono<IdNameDTO> payload) {
        return payload
                .flatMap(it -> {
                    if (previous.contains(it.getName()))
                        return Mono.error(ServiceException.newInstance4XX("No puede volver a ingresar un registro previamente eliminado"));

                    return Mono.just(it);
                }).map(IdNameDTO::toEntity)
                .flatMap(idNameService::insert)
                .map(IdNameDTO::from);
    }

    @Override
    public Mono<IdNameDTO> updateById(Long id, Mono<IdNameDTO> payload) {
        return payload.map(it -> it.toEntity(id))
                .flatMap(idNameService::update)
                .map(IdNameDTO::from);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return Mono.just(id)
                .doOnNext(unused ->
                        idNameService.getById(id)
                                .map(IdName::getName)
                                .doOnNext(previous::add)
                                .subscribe(s -> log.info("added {}", s),
                                        e -> log.error("error {}", e.getMessage()))
                ).flatMap(idNameService::deleteById);
    }

}
