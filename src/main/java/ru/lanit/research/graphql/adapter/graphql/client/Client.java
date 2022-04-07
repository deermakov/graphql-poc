package ru.lanit.research.graphql.adapter.graphql.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.lanit.research.graphql.domain.Deal;

@Component
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

    @Scheduled(fixedDelay = 5000)
    public void getAllDeals() {

        WebClient webClient = WebClient.create("http://localhost:9900/graphql");
        HttpGraphQlClient graphQlClient = HttpGraphQlClient.create(webClient);

        Mono<Deal> projectMono = graphQlClient.document(queryAllDeals)
            .retrieve("getAllDeals[0]")
            .toEntity(Deal.class);

        log.info("Result = {}", projectMono.block());
    }
}
