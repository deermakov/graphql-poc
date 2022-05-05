# graphql-poc
PoC GraphQL, включая:
- обычный GraphQL over HTTP: localhost:9900/graphql
- GraphQL over Kafka (запросы и ответы, см. Client, MessageProducer, MessageConsumer)

Клиент и сервер GraphQL реализованы на Spring for GraphQL: https://spring.io/projects/spring-graphql

В проекте используется testcontainers для PostgreSQL и Kafka, поэтому для запуска нужен Docker

Примеры запросов:

    Запрос всех сделок:
    query {
        getAllDeals {
            id,
            num,
            sum,
            participants {
                id,
                inn,
                name
            }
        }
    }

    Запрос всех участников:
    {
        getAllLegalEntities {
            id
            inn
            name
            deal {
                id,
                num
            }
        }
    }

    Создание новой сделки с новыми участниками:
    mutation M($num: String!, $sum: Float, $participants: [LegalEntityInput]){
        writeDeal(num: $num, sum: $sum, participants: $participants) {
            num
            sum
            participants {
                id,
                inn,
                name
            }
        }
    }
    + параметры:
    {
        "num": "Моя сделка",
        "sum": 111,
        "participants": [
            {
                "inn": "INN1"
            },
            {
                "inn": "INN2"
            }
        ]
    }

    Обновление существующей сделки с обновлением существующих участников:
    mutation M($id: ID, $num: String!, $sum: Float, $participants: [LegalEntityInput]){
        writeDeal(id: $id, num: $num, sum: $sum, participants: $participants) {
            num
            sum
            participants {
                inn,
                name
            }
        }
    }
    + параметры:
    {
        "id": "00000001-b0d9-11ec-b909-0242ac120002",
        "num": "Сделка 1a",
        "sum": 111,
        "participants": [
        {
            "id": "10000001-b0d9-11ec-b909-0242ac120002",
            "inn": "INN1a"
        },
        {
            "id": "10000002-b0d9-11ec-b909-0242ac120002",
            "inn": "INN2a"
        }
        ]
    }

    Перепривязать участника от первой сделки ко второй:
    mutation M($legalEntity: LegalEntityInput){
        writeLegalEntity(legalEntity: $legalEntity) {
            id
            inn
            name
            deal {
                id
            }
        }
    }
    + параметры:
    {
        "legalEntity":{
            "id": "10000002-b0d9-11ec-b909-0242ac120002",
            "deal":
            {
                "id": "00000002-b0d9-11ec-b909-0242ac120002"
            }
        
        }
    }
