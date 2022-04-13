package ru.lanit.research.graphql.fw;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.lanit.research.graphql.adapter.kafka.dto.KafkaGraphQlRequest;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, KafkaGraphQlRequest> myKafkaTemplate(ProducerFactory<String, KafkaGraphQlRequest> myProducerFactory) {
        return new KafkaTemplate<>(myProducerFactory);
    }
}
