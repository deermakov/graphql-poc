package ru.lanit.research.graphql.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;

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
@DiscriminatorValue("P")
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person extends Party {

    private String fio;

    @Override
    public DomainObject mergeToDb(EntityManager entityManager) {
        return super.mergeToDb(entityManager);
    }
}
