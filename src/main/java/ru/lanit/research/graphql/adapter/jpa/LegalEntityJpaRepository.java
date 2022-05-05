package ru.lanit.research.graphql.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.graphql.domain.LegalEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LegalEntityJpaRepository extends CrudRepository<LegalEntity, UUID> {
    Optional<LegalEntity> findByInn(String inn);
    List<LegalEntity> findAllByOrderByInn();
}
