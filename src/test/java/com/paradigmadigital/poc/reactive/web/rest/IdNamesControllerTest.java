package com.paradigmadigital.poc.reactive.web.rest;

import com.paradigmadigital.poc.reactive.idnames.IdName;
import com.paradigmadigital.poc.reactive.idnames.IdNameService;
import com.paradigmadigital.poc.reactive.web.model.IdNameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = IdNamesController.class)
class IdNamesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IdNameService idNameService;

    @Test
    void getById_WhenIdExists_ThenReturningOK() {
        final IdName idName = IdName.of(0L, "test value");

        given(idNameService.getById(anyLong())).willReturn(Mono.just(idName));

        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/idNames/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(IdNameDTO.class).value(it ->
                        assertThat(it.getName()).isEqualTo(idName.getName())
                );

    }

    @Test
    void getById_WhenIdNoExists_ThenReturningNotFound() {
        final IdName idName = IdName.of(0L, "test value");

        given(idNameService.getById(anyLong())).willReturn(Mono.empty());

        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/idNames/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isNotFound();

    }
}