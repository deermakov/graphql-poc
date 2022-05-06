# graphql-poc
PoC GraphQL, включая:
- обычный GraphQL over HTTP: localhost:9900/graphql
- GraphQL over Kafka (запросы и ответы, см. Client, MessageProducer, MessageConsumer)
- идентификация сущностей по суррогатному и бизнес-ключу
- обновление графа сущностей, включая обновление (в т.ч. перепривязку) вложенных сущностей

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
    mutation M($deal: DealInput){
        writeDeal(deal: $deal) {
            id
            num
            sum
            participants {
                id,
                inn,
                name,
                deal{
                    id
                }
            }
        }
    }
    + параметры:
    {
        "deal": {
            "num": "Моя сделка",
            "sum": 111,
            "participants": [
                {
                    "inn": "INN1",
                    "name": "Новый - 1"
                },
                {
                    "inn": "INN2",
                    "name": "Новый - 2"
                }
            ]
        }
    }    

    Создание новой сделки с обновлением существующих участников (у них обновляются name'ы и
    они перепривязываются от существующей сделки к этой новой). Идентификация участников по бизнес-ключам:
    mutation M($deal: DealInput){
        writeDeal(deal: $deal) {
            id
            num
            sum
            participants {
                id,
                inn,
                name,
                deal{
                    id
                }
            }
        }
    }
    + параметры:
    {
        "deal": {
            "num": "Моя сделка 2",
            "sum": 111,
            "participants": [
                {
                    "inn": "100",
                    "name": "Альфа UPD"
                },
                {
                    "inn": "200",
                    "name": "Бета UPD"
                }
            ]
        }
    }

    Обновление существующей сделки (sum) с обновлением существующих участников (name'ы).
    Идентификация сделки и участников - по id:
    mutation M($deal: DealInput){
        writeDeal(deal: $deal) {
            id
            num
            sum
            participants {
                id,
                inn,
                name,
                deal{
                    id
                }
            }
        }
    }
    + параметры:
    {
        "deal": {
            "id": "00000001-b0d9-11ec-b909-0242ac120002",
            "sum": 999,
            "participants": [
                {
                    "id": "10000001-b0d9-11ec-b909-0242ac120002",
                    "name": "Альфа X"
                },
                {
                    "id": "10000002-b0d9-11ec-b909-0242ac120002",
                    "name": "Бета X"
                }
            ]
        }
    }

    Перепривязать участника от первой сделки ко второй.
    !!! ЭТО Deprecated, т.к. обновление participant (в т.ч. перепривязка) поддерживается в рамках метода writeDeal():
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
    + параметры (идентификация по id):
    {
        "legalEntity":{
            "id": "10000002-b0d9-11ec-b909-0242ac120002",
            "deal":
            {
                "id": "00000002-b0d9-11ec-b909-0242ac120002"
            } 
        }
    }
     ... или параметры (идентификация по бизнес-ключу):
    {
        "legalEntity":{
            "inn": "200",
            "deal":
            {
                "id": "00000002-b0d9-11ec-b909-0242ac120002"
            } 
        }
    }
