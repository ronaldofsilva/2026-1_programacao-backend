package br.ueg.posse.tsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
    info = @Info(
        title = "People API",
        version = "1.0.0",
        description = "API REST para gerenciamento de pessoas e fotos",
        contact = @Contact(
            name = "Universidade Estadual de Goiás",
            email = "ronaldo.ferreira@ueg.br"
        )
    )
)

@EnableMongoRepositories(basePackages = "br.ueg.posse.tsi.repositories")
@SpringBootApplication
@EnableMongoAuditing 
public class BackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}
