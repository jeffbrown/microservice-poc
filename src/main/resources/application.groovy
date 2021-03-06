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
            login {
                enabled = true
            }
        }
        token {
            jwt {
                enabled = true
                bearer {
                    enabled = true
                }
            }
        }
    }
}
