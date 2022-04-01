package ru.lanit.research.graphql.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.research.graphql.adapter.jpa.DealJpaRepository;
import ru.lanit.research.graphql.adapter.jpa.LegalEntityJpaRepository;
import ru.lanit.research.graphql.domain.Deal;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private final DealJpaRepository dealJpaRepository;
    private final LegalEntityJpaRepository legalEntityJpaRepository;

    public Deal writeDeal(String num, BigDecimal sum) {
        Deal deal = Deal.builder().num(num).sum(sum).build();
        return dealJpaRepository.save(deal);
    }
}
