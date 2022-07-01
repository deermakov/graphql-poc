package ru.lanit.research.graphql.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.graphql.domain.LegalEntity;
import ru.lanit.research.graphql.domain.Pledge;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PledgeJpaRepository extends CrudRepository<Pledge, UUID> {
    List<Pledge> findAll();
}
