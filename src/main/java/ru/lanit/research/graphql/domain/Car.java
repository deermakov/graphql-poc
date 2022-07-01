package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@DiscriminatorValue("Car")
@Data
public class Car extends Pledge {
    private String vin;

    @Override
    Object getBusinessKey() {
        return vin;
    }
}
