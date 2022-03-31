package ru.lanit.research.graphql.app.api;

import ru.lanit.research.graphql.domain.SourceEntity;

/**
 * Компонент, отправляющий сообщения в Kafka
 */
public interface MessageProducer {
    void send(SourceEntity sourceEntity);
}
