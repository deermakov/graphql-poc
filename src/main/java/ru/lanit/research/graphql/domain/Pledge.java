package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.*;

@Data
//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="pledge_type",
    discriminatorType = DiscriminatorType.STRING)
public abstract class Pledge extends DomainObject {
    private String description;
}
