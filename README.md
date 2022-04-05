# graphql-poc
PoC GraphQL
В проекте используется testcontainer для PostgreSQL, поэтому для запуска нужен Docker"# graphql-poc"

GraphiQL:

    UI: http://localhost:9900/graphiql

    запрос всех сделок:
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

    запрос всех участников:
    {
        allLegalEntities {
            id
            inn
            deal {
                id
            }
        }
    }

    сохранение сделки с участниками:
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


GraphQL:

    GET запрос всех сделок (НЕ РАБОТАЕТ): http://localhost:9900/graphql?query={allDeals{num,sum}}

