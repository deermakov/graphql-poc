# graphql-poc
PoC GraphQL
В проекте используется testcontainer для PostgreSQL, поэтому для запуска нужен Docker"# graphql-poc"

GraphiQL:

    UI: http://localhost:9900/graphiql

    Запрос всех сделок:
    query {
        allDeals {
            id,
            num,
            sum,
            participants {
                id
            }
        }
    }

    Запрос всех участников:
    {
        allLegalEntities {
            id
            inn
            deal {
                id
            }
        }
    }

    Создание новой сделки с новыми участниками:
    mutation M($num: String!, $sum: Float, $participants: [LegalEntityInput]){
        writeDeal(num: $num, sum: $sum, participants: $participants) {
            num
            sum
            participants {
                inn
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
                inn
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

GraphQL:

    GET запрос всех сделок (НЕ РАБОТАЕТ): http://localhost:9900/graphql?query={allDeals{num,sum}}

