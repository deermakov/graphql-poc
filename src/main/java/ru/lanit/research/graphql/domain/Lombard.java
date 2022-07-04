package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Lombard")
@Data
public class Lombard extends PledgeHolder {
    private String regNumber;

    @Override
    Object getBusinessKey() {
        return regNumber;
    }
}
