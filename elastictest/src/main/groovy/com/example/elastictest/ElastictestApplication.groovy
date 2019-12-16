package com.example.elastictest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.example.elastictest"])
class ElastictestApplication {

	static void main(String[] args) {
		SpringApplication.run(ElastictestApplication, args)
	}

}
//docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.8.5
