micronaut.application.name='microservicepoc'
micronaut.server.port=8080
hibernate {
    hbm2ddl {
        auto = 'update'
    }
}

micronaut {
    security {
        enabled = true
        endpoints {
            login = true
        }
        token {
            jwt {
                bearer {
                    enabled = true
                }
                enabled = true
            }
        }
    }
}
