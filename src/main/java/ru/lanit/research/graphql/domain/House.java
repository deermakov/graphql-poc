package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("House")
@Data
public class House extends Pledge {
    private String cadaster;

    @Override
    Object getBusinessKey() {
        return cadaster;
    }
}
