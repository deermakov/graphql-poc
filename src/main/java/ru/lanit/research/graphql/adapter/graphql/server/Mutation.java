package ru.lanit.research.graphql.adapter.graphql.server;

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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Mutation {
    private final DealJpaRepository dealJpaRepository;
    private final LegalEntityJpaRepository legalEntityJpaRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @MutationMapping
    @Transactional
    public Deal writeDeal(@Argument Deal deal) {
        Deal mergedDeal = (Deal) deal.mergeToDb(entityManager);
        return dealJpaRepository.save(mergedDeal);
    }

    @MutationMapping
    @Transactional
    @Deprecated // т.к. обновление participant (в т.ч. перепривязка) поддерживается в рамках метода writeDeal()
    public LegalEntity writeLegalEntity(@Argument LegalEntity legalEntity) throws Exception {
        // вычитывание существующей сущности из БД (или создание новой, если не нашли)
        UUID id = legalEntity.getId();
        String inn = legalEntity.getInn();
        LegalEntity base = null;
        if (id != null) // ...по id
        {
            base = legalEntityJpaRepository.findById(id).orElse(new LegalEntity());
        } else if (inn != null) // ... по бизнес-ключу
        {
            base = legalEntityJpaRepository.findByInn(inn).orElse(new LegalEntity());
        }

        // merge сущности из запроса в сущность в БД
        BeanMerger.shallowMerge(legalEntity, base);

        // сохранение обновленной сущности в БД
        // todo может быть попробовать возложить merge на GraphQL/JPA
        return legalEntityJpaRepository.save(base);
    }
}
