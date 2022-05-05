package ru.lanit.research.graphql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEAL")
public class Deal extends DomainObject {
    private String num; // это бизнес-ключ
    private BigDecimal sum;

    // todo попробовать уйти от EAGER
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "deal") // без EAGER вылетает ошибка при запросе deal.participants
    private List<LegalEntity> participants;
}
