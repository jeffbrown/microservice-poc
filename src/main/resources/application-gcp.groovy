dataSource {
    url = 'jdbc:postgresql://google/people?socketFactory=com.google.cloud.sql.postgres.SocketFactory&socketFactoryArg=microservice-poc-202623:us-central1:postgres'
    username = System.getenv('PG_USERNAME')
    password = System.getenv('PG_PASSWORD')
}

