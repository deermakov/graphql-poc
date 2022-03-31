package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Полученная сущность
 */
@Data
@Entity
@Table(name = "LEGAL_ENTITY")
public class LegalEntity extends DomainObject {
    @ManyToOne
    @JoinColumn(name="deal_id", nullable=true)
    private Deal deal;

    private String inn;
}
