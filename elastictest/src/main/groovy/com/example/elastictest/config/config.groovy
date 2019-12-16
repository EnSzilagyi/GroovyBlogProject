package com.example.elastictest.config


import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories(basePackages = ["com.example.elastictest.user.repository","com.example.elastictest.article.repository"])
@ComponentScan(basePackages =  ["com.example.elastictest.article.service","com.example.elastictest.user.service"] )
class Config {

    @Bean
    Client client() {
        Settings settings = Settings.builder()
                .put("cluster.name", "docker-cluster")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("0.0.0.0")
                , 9300));
        return client;
    }
}
