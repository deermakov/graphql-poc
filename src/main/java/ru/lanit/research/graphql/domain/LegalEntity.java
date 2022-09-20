package ru.lanit.research.graphql.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
//@DynamicUpdate
//@SelectBeforeUpdate
@Table(name = "PARTY")
@DiscriminatorValue("LE")
@Slf4j
public class LegalEntity extends Party {

    @Override
    Object getBusinessKey() {
        return getInn();
    }

    @Override
    public DomainObject mergeToDb(EntityManager entityManager) {
        return super.mergeToDb(entityManager);
    }
}
