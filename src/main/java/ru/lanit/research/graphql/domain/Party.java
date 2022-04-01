package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Party extends DomainObject {
    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    protected Deal deal;
}
