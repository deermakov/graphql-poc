package ru.lanit.research.graphql.app.api;

/**
 * Компонент, отправляющий сообщения из хранилища-источника в Kafka
 */
public interface SenderService {
    void sendNextEntity();
}
