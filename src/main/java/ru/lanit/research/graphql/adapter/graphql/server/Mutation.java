package ru.lanit.research.graphql.adapter.graphql.server;

import liquibase.pro.packaged.L;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.research.graphql.adapter.jpa.DealJpaRepository;
import ru.lanit.research.graphql.adapter.jpa.LegalEntityJpaRepository;
import ru.lanit.research.graphql.app.impl.BeanMerger;
import ru.lanit.research.graphql.domain.Deal;
import ru.lanit.research.graphql.domain.LegalEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Mutation {
    private final DealJpaRepository dealJpaRepository;
    private final LegalEntityJpaRepository legalEntityJpaRepository;

    @MutationMapping
    public Deal writeDeal(@Argument UUID id, @Argument String num, @Argument BigDecimal sum, @Argument List<LegalEntity> participants) {
        Deal deal = Deal.builder().id(id).num(num).sum(sum).participants(participants).build();
        // т.к. ссылка на Deal лежит в LegalEntity, надо ее заполнить перед сохранением
        participants.stream().forEach(legalEntity -> legalEntity.setDeal(deal));
        return dealJpaRepository.save(deal);
    }

    @MutationMapping
    @Transactional
    public LegalEntity writeLegalEntity(@Argument LegalEntity legalEntity) throws Exception {
        // вычитывание существующей сущности из БД (или создание новой, если не нашли)
        LegalEntity base = legalEntityJpaRepository.findById(legalEntity.getId()).orElse(new LegalEntity());
        // merge сущности из запроса в сущность в БД
        BeanMerger.shallowMerge(legalEntity, base);
        // сохранение обновленной сущности в БД
        // todo может быть попробовать возложить merge на GraphQL/JPA
        return legalEntityJpaRepository.save(base);
    }
}
