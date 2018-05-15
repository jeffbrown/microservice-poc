dataSource {
    String metadataUrlRoot = 'http://metadata.google.internal/computeMetadata/v1/project/attributes'
    Map requestHeaders = [requestProperties: ['Metadata-Flavor': 'Google']]
    url      = "${metadataUrlRoot}/PG_JDBC_URL".toURL().getText(requestHeaders)
    username = "${metadataUrlRoot}/PG_USERNAME".toURL().getText(requestHeaders)
    password = "${metadataUrlRoot}/PG_PASSWORD".toURL().getText(requestHeaders)
}

