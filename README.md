# graphql-poc
PoC GraphQL, включая:
- обычный GraphQL over HTTP: localhost:9900/graphql
- GraphQL over Kafka (запросы и ответы, см. Client, MessageProducer, MessageConsumer)
- идентификация сущностей по суррогатному и бизнес-ключу
- обновление графа сущностей, включая обновление (в т.ч. перепривязку) вложенных сущностей
- полиморфизм при чтении (query): на стороне отправителя (GQL types implements и query fragments) и на стороне получателя (JSON parsing на основе @JsonTypeInfo, см. Party).
- полиморфизм при записи (mutation) не получается ! Поэтому вместо одной операции writeDeal() со вложенными (полиморфными) participants приходится делать writeDeal() без participants и потом writeLegalEntity() для сохранения конкретно ЮЛ

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
                __typename,
                id,
                inn,
                name
            }
        }
    }

    Запрос сделок по "num or sum":
    query {
        getDealsByNumOrSum(num: "# 1", sum: 200){
            id,
            num,
            sum
        }
    }

    Запрос всех участников-ЮЛ:
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

    ! НЕ РАБОТАЕТ (см. выше про полиморфизм) !
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

    ! НЕ РАБОТАЕТ (см. выше про полиморфизм) !
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

    ! НЕ РАБОТАЕТ (см. выше про полиморфизм) !
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

    Перепривязать (+ переименовать) участника от первой сделки ко второй.
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
            "name": "Переименовано",
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
            "name": "Переименовано",
            "deal":
            {
                "id": "00000002-b0d9-11ec-b909-0242ac120002"
            } 
        }
    }

    Получить все залоги (запрос с полиморфным результатом)
    query {
        getAllPledges {
            id
            __typename
            description
            ... on Car {
                vin
            }
            ... on House {
                cadaster
            }
            pledgeHolder {
                id
                __typename
                name
                ... on Bank {
                    bic
                }
                ... on Lombard {
                    regNumber
                }
            }
        }
    }

    Получить все залоги - тот же запрос, но с использованием fragment
    fragment PledgeFields on Pledge {
        id
        __typename
        description
        pledgeHolder {
            id
            __typename
            name
        }
    }

    query {
        getAllPledges {
            ...PledgeFields
            ... on Car {
                vin
            }
            ... on House {
                cadaster
            }
            pledgeHolder {
                ... on Bank {
                    bic
                }
                ... on Lombard {
                    regNumber
                }
            }
        }
    }