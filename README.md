# graphql-poc
PoC GraphQL
В проекте используется testcontainer для PostgreSQL, поэтому для запуска нужен Docker"# graphql-poc"

GraphiQL:

    UI: http://localhost:9900/graphiql

    запрос всех сделок:
    {
        allDeals {
            num,
            sum,
            participants
        }
    }

    сохранение сделки:
    mutation {
        writeDeal(num: "1", sum: 1) {
            num
            sum
        }
    }

GraphQL:

    GET запрос всех сделок (НЕ РАБОТАЕТ): http://localhost:9900/graphql?query={allDeals{num,sum}}

