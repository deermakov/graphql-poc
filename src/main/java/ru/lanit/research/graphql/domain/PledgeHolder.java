package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.*;

@Data
//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="pledge_holder_type",
    discriminatorType = DiscriminatorType.STRING)
public abstract class PledgeHolder extends DomainObject {
    private String name;
}
