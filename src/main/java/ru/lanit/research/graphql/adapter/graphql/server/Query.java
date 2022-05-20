package ru.lanit.research.graphql.adapter.graphql.server;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.lanit.research.graphql.adapter.jpa.DealJpaRepository;
import ru.lanit.research.graphql.adapter.jpa.LegalEntityJpaRepository;
import ru.lanit.research.graphql.domain.Deal;
import ru.lanit.research.graphql.domain.LegalEntity;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class Query {
    private final DealJpaRepository dealJpaRepository;
    private final LegalEntityJpaRepository legalEntityJpaRepository;

    @QueryMapping
    public List<Deal> getAllDeals() {
        //if (true) throw new RuntimeException("AAARRR");
        return dealJpaRepository.findAllByOrderByNum();
    }

    @QueryMapping
    public List<Deal> getDealsByNumOrSum(@Argument String num, @Argument BigDecimal sum) {
        //if (true) throw new RuntimeException("AAARRR");
        return dealJpaRepository.findAllByNumOrSum(num, sum);
    }

    @QueryMapping
    public List<LegalEntity> getAllLegalEntities() {
        return legalEntityJpaRepository.findAllByOrderByInn();
    }
}
