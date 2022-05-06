package ru.lanit.research.graphql.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Полученная сущность
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
//@DynamicUpdate
//@SelectBeforeUpdate
@Table(name = "LEGAL_ENTITY")
@Slf4j
public class LegalEntity extends DomainObject {
    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    @ToString.Exclude
    private Deal deal;

    @NaturalId // это бизнес-ключ
    private String inn;

    private String name;

    @Override
    Object getBusinessKey() {
        return inn;
    }

    @Override
    public DomainObject mergeToDb(EntityManager entityManager) {
        return super.mergeToDb(entityManager);
    }
}
