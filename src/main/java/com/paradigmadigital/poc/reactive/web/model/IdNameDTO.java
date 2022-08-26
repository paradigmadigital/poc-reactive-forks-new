package com.paradigmadigital.poc.reactive.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paradigmadigital.poc.reactive.idnames.IdName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdNameDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String name;

    public static IdNameDTO from(IdName entity) {
        return IdNameDTO.of(entity.getId(), entity.getName());
    }

    public IdName toEntity() {
        return IdName.of(id, name);
    }

    public IdName toEntity(Long id) {
        return IdName.of(id, name);
    }

}
