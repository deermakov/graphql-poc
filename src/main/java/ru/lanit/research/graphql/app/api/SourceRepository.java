package ru.lanit.research.graphql.app.api;

import ru.lanit.research.graphql.domain.SourceEntity;

/**
 * Компонент для работы с хранилищем отправляемых сообщений
 */
public interface SourceRepository {
    SourceEntity fetchNext();
}
