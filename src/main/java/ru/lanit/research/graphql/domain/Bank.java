package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Bank")
@Data
public class Bank extends PledgeHolder {
    private String bic;

    @Override
    Object getBusinessKey() {
        return bic;
    }
}
