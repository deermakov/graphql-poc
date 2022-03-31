package ru.lanit.research.graphql.app.api;

import ru.lanit.research.graphql.domain.SourceEntity;

/**
 * Компонент, сохраняющий сообщения в хранилище-получателе
 */
public interface ReceiverService {

    void receiveEntity(SourceEntity sourceEntity);

}
