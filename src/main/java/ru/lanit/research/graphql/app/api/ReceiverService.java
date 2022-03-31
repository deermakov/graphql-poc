package ru.lanit.research.graphql.app.api;

import ru.lanit.research.graphql.domain.Deal;

/**
 * Компонент, сохраняющий сообщения в хранилище-получателе
 */
public interface ReceiverService {

    void receiveEntity(Deal sourceEntity);

}
