package ru.lanit.research.graphql.adapter.graphql.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.lanit.research.graphql.adapter.kafka.client.MessageProducer;
import ru.lanit.research.graphql.adapter.kafka.dto.KafkaGraphQlRequest;
import ru.lanit.research.graphql.domain.Deal;

@Component
@RequiredArgsConstructor
@Slf4j
public class Client {

    String queryAllDeals = "    query {\n" +
        "        getAllDeals {\n" +
        "            id,\n" +
        "            num,\n" +
        "            sum,\n" +
        "            participants {\n" +
        "                id,\n" +
        "                inn\n" +
        "            }\n" +
        "        }\n" +
        "    }\n";

    private final MessageProducer messageProducer;

    private boolean executed;

    @Scheduled(fixedDelay = 5000)
    public void getAllDeals() {

        if (executed) {
            return;
        }
        executed = true;

        // Синхронный вызов
        WebClient webClient = WebClient.create("http://localhost:9900/graphql");
        HttpGraphQlClient graphQlClient = HttpGraphQlClient.create(webClient);

        Mono<Deal> projectMono = graphQlClient.document(queryAllDeals)
            .retrieve("getAllDeals[0]")
            .toEntity(Deal.class);

        log.info("GraphQL sync response = {}", projectMono.block());

        // Асинхронный вызов
        KafkaGraphQlRequest request = new KafkaGraphQlRequest(queryAllDeals, null, null);
        messageProducer.send(request);
    }
}
