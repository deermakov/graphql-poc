package ru.lanit.research.graphql.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.graphql.domain.TargetEntity;

import java.util.List;

public interface TargetJpaRepository extends CrudRepository<TargetEntity, Long> {
    List<TargetEntity> findAllByOrderByPosition();
}
