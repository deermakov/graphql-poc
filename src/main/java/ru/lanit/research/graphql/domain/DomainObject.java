package ru.lanit.research.graphql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
