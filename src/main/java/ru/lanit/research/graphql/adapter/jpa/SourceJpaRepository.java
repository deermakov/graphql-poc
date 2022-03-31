package ru.lanit.research.graphql.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.graphql.domain.SourceEntity;

public interface SourceJpaRepository extends CrudRepository<SourceEntity, Long> {
    SourceEntity findFirstByProcessedIsFalseOrderByText();
}
