package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Полученная сущность
 */
@Data
@Entity
@Table(name = "LEGAL_ENTITY")
public class LegalEntity extends Party {
    private String inn;
}
