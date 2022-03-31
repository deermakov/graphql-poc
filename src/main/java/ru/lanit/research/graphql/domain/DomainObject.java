package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class DomainObject {
    @Id
    @GeneratedValue
    private UUID id;
}
