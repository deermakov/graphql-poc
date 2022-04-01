package ru.lanit.research.graphql.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEAL")
public class Deal extends DomainObject {
    private String num;
    private BigDecimal sum;

    @OneToMany(mappedBy = "deal", fetch = FetchType.EAGER) // без EAGER вылетает ошибка при запросе deal.participants
    private List<LegalEntity> participants;
}
