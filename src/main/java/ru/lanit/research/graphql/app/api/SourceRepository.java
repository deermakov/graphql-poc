package ru.lanit.research.graphql.app.api;

import ru.lanit.research.graphql.domain.Deal;

/**
 * Компонент для работы с хранилищем отправляемых сообщений
 */
public interface SourceRepository {
    Deal fetchNext();
}
