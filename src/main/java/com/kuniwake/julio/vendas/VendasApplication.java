package com.kuniwake.julio.vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {
//
//	@Value("${application.name}")
//	private String applicationName;
//
//	@Cachorro
//	private Animal animal;
//
//	@Bean(name = "executarAnimal")
//	public CommandLineRunner executarAnimal(){
//		return args -> {
//			this.animal.fazerBarulho();
//		};
//	}
//
//	@GetMapping("hello")
//	public String hello(){
//		return applicationName;
//	}


	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
