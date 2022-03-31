package ru.lanit.research.graphql.app.api;

import ru.lanit.research.graphql.domain.TargetEntity;

import java.util.List;

/**
 * Компонент для работы с хранилищем полученных сообщений
 */
public interface TargetRepository {
    void save(TargetEntity targetEntity);

    List<String> listAll();
}
