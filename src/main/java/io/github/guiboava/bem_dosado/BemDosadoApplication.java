package io.github.guiboava.bem_dosado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BemDosadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BemDosadoApplication.class, args);
	}

}
