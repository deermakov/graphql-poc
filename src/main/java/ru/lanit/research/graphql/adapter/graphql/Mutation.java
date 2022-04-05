package ru.lanit.research.graphql.adapter.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.research.graphql.adapter.jpa.DealJpaRepository;
import ru.lanit.research.graphql.adapter.jpa.LegalEntityJpaRepository;
import ru.lanit.research.graphql.domain.Deal;
import ru.lanit.research.graphql.domain.LegalEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private final DealJpaRepository dealJpaRepository;
    private final LegalEntityJpaRepository legalEntityJpaRepository;

    public Deal writeDeal(UUID id, String num, BigDecimal sum, List<LegalEntity> participants) {
        Deal deal = Deal.builder().id(id).num(num).sum(sum).participants(participants).build();
        return dealJpaRepository.save(deal);
    }
}
