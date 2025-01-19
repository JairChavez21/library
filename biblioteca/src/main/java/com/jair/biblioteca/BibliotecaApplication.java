package com.jair.biblioteca;

import com.jair.biblioteca.principal.Principal;
import com.jair.biblioteca.repository.LibrosBDRepository;
import com.jair.biblioteca.repository.PersonsBDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {

	@Autowired
	private LibrosBDRepository repository;
	@Autowired
	private PersonsBDRepository repositoryP;
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, repositoryP);
		principal.iniciarApp();
	}
}
