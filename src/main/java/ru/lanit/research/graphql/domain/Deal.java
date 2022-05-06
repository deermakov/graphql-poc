package ru.lanit.research.graphql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEAL")
public class Deal extends DomainObject {
    @NaturalId // это бизнес-ключ
    private String num;

    private BigDecimal sum;

    // todo попробовать уйти от EAGER
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "deal") // без EAGER вылетает ошибка при запросе deal.participants
    private List<LegalEntity> participants;

    @Override
    Object getBusinessKey() {
        return num;
    }

    @Override
    public DomainObject mergeToDb(EntityManager entityManager) {
        // merge себя
        Deal mergedDeal = (Deal) super.mergeToDb(entityManager);

        // merge участников
        List<LegalEntity> mergedParticipants = new ArrayList<>();
        participants.stream().forEach(legalEntity -> {
            LegalEntity mergedLegalEntity = (LegalEntity) legalEntity.mergeToDb(entityManager);
            mergedLegalEntity.setDeal(mergedDeal);// установим в него ссылку на сделку
            mergedParticipants.add(mergedLegalEntity);
        });
        mergedDeal.setParticipants(mergedParticipants);

        return mergedDeal;
    }
}
