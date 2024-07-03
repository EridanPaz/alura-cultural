package com.alura.alura_cultural;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraCulturalApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AluraCulturalApplication.class, args);
		}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("Só um teste...");
	}
}
