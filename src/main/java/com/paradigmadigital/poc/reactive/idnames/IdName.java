package com.paradigmadigital.poc.reactive.idnames;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import java.beans.Transient;

@AllArgsConstructor(staticName = "of")
@Getter
@Table
public class IdName implements Persistable<Long> {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    @Override
    @Transient
    public boolean isNew() {
        return id == null;
    }
}
