package com.paradigmadigital.poc.reactive.idnames;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.PrematureCloseException;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
class IdNameServiceImpl implements IdNameService {

    private final IdNameRepository idNameRepository;

    private final Retry catchingQueryTimeoutException = Retry.fixedDelay(3, Duration.ofSeconds(2))
            .filter(it -> it instanceof PrematureCloseException || it instanceof QueryTimeoutException)
            .doAfterRetry(retrySignal ->
                log.info("iteration: {}, cause: {} {}", retrySignal.totalRetriesInARow(), retrySignal.failure().getClass().toString(), retrySignal.failure().getMessage())
            ).onRetryExhaustedThrow((retrySpec, retrySignal) -> retrySignal.failure());

    @Override
    public Flux<IdName> getAll() {
        return idNameRepository.findAll()
                .retryWhen(catchingQueryTimeoutException);
    }

    @Override
    public Mono<IdName> getById(Long id) {
        return idNameRepository.findById(id)
                .retryWhen(catchingQueryTimeoutException);
    }

    @Override
    public Mono<IdName> insert(IdName payload) {
        return idNameRepository.save(payload)
                .retryWhen(catchingQueryTimeoutException);
    }

    @Override
    public Mono<IdName> update(IdName payload) {
        return idNameRepository.save(payload)
                .retryWhen(catchingQueryTimeoutException);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return idNameRepository.deleteById(id);
    }

}
