package ru.lanit.research.graphql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Полученная сущность
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "LEGAL_ENTITY")
public class LegalEntity extends DomainObject {
    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    @ToString.Exclude
    private Deal deal;

    private String inn; // это бизнес-ключ
    private String name;
}
