package ru.lanit.research.graphql.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "PARTY")
@NoArgsConstructor
@Entity
@SuperBuilder
@ToString(callSuper = true)
//@DynamicUpdate
//@SelectBeforeUpdate
@Slf4j
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="__typename")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LegalEntity.class, name = "LegalEntity"),
    @JsonSubTypes.Type(value = Person.class, name = "Person")
})
public abstract class Party extends DomainObject {
    @NaturalId // это бизнес-ключ
    private String inn;

    private String name;

    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    @ToString.Exclude
    private Deal deal;
}
