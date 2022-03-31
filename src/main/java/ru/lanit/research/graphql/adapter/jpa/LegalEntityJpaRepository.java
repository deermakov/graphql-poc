package ru.lanit.research.graphql.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.graphql.domain.LegalEntity;
import ru.lanit.research.graphql.domain.Party;

import java.util.List;
import java.util.UUID;

public interface LegalEntityJpaRepository extends CrudRepository<LegalEntity, UUID> {
    List<LegalEntity> findAllByOrderByInn();
}
