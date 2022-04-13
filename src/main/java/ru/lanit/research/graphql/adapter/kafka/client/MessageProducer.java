package ru.lanit.research.graphql.adapter.kafka.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.lanit.research.graphql.adapter.kafka.dto.KafkaGraphQlRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final KafkaTemplate<String, KafkaGraphQlRequest> kafkaTemplate;

    @Value("${myservice.topics.main-topic}")
    private String mainTopic;

    public void send(KafkaGraphQlRequest request) {
        String key = "123";
        kafkaTemplate.send(mainTopic, key, request);
    }
}