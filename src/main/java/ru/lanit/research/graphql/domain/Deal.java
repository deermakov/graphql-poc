package ru.lanit.research.graphql.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "DEAL")
public class Deal extends DomainObject {
    private String num;
    private BigDecimal sum;

    @OneToMany(mappedBy = "deal", fetch = FetchType.EAGER) // без EAGER вылетает ошибка при запросе deal.participants
    private List<LegalEntity> participants;
}
