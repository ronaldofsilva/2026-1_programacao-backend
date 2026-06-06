package br.ueg.posse.tsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "br.ueg.posse.tsi.repositories")
@SpringBootApplication
@EnableMongoAuditing 
public class BackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}
