package ru.lanit.research.graphql.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.research.graphql.adapter.jpa.DealJpaRepository;
import ru.lanit.research.graphql.adapter.jpa.LegalEntityJpaRepository;
import ru.lanit.research.graphql.domain.Deal;
import ru.lanit.research.graphql.domain.LegalEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final DealJpaRepository dealJpaRepository;
    private final LegalEntityJpaRepository legalEntityJpaRepository;

    public List<Deal> getAllDeals() {
        return dealJpaRepository.findAllByOrderByNum();
    }
    public List<LegalEntity> getAllLegalEntities() {
        return legalEntityJpaRepository.findAllByOrderByInn();
    }

}
