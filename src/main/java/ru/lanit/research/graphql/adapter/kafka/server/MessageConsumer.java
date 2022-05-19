package ru.lanit.research.graphql.adapter.kafka.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.ExecutionGraphQlRequest;
import org.springframework.graphql.ExecutionGraphQlResponse;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.support.DefaultExecutionGraphQlRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.lanit.research.graphql.adapter.kafka.dto.KafkaGraphQlRequest;
import ru.lanit.research.graphql.domain.Deal;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final ExecutionGraphQlService graphQlService;

    @KafkaListener(
        topics = "${myservice.topics.main-topic}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void receiveEntity(KafkaGraphQlRequest request) {
        log.info("Kafka request received: {}", request);

        String id = "42";
        ExecutionGraphQlRequest execution =
            new DefaultExecutionGraphQlRequest(request.getDocument(), request.getOperationName(), request.getVariables(), null, id, null);
        Mono<ExecutionGraphQlResponse> response = graphQlService.execute(execution);

        Object result = response.block().field("getAllDeals[0]").getValue();//.getExecutionResult().toSpecification().get("data")).get("getAllDeals");

        try {
            String json = new ObjectMapper().writeValueAsString(result);
            Deal deal = new ObjectMapper().readValue(json, Deal.class);

            log.info("GraphQL async response = {}", deal);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
