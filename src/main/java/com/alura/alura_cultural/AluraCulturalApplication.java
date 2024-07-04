package com.alura.alura_cultural;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.alura.alura_cultural.principal.Principal;
import com.alura.alura_cultural.repository.Repository;

@SpringBootApplication
public class AluraCulturalApplication implements CommandLineRunner {

	@Autowired
	private Repository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(AluraCulturalApplication.class, args);
		}

	@Override
	public void run(String... args) throws Exception{
		Principal principal = new Principal(repository);
		principal.menu();
	}
}
