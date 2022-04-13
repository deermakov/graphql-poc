package ru.lanit.research.graphql.adapter.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaGraphQlRequest {

    private String document;
    @Nullable
    private String operationName;
    private Map<String, Object> variables;
}