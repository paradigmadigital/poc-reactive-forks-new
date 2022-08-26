package com.paradigmadigital.poc.reactive.idnames;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class IdNameServiceImplTest {

    private IdNameService idNameService;
    @Mock
    private IdNameRepository idNameRepository;

    @BeforeEach
    public void setUp() {
        idNameService = new IdNameServiceImpl(idNameRepository);
    }

    @Test
    void findById_whenIdExists_thenReturningIdName() {
        final IdName idName = IdName.of(0L, "test value");

        given(idNameRepository.findById(anyLong())).willReturn(Mono.just(idName));

        StepVerifier.create(idNameService.getById(idName.getId()))
                .expectNext(idName)
                .verifyComplete();

    }
}