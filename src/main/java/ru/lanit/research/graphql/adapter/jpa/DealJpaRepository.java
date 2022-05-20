package ru.lanit.research.graphql.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.graphql.domain.Deal;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface DealJpaRepository extends CrudRepository<Deal, UUID> {
    List<Deal> findAllByOrderByNum();
    List<Deal> findAllByNumOrSum(String num, BigDecimal sum);
}
