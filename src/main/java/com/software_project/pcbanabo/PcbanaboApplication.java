package com.software_project.pcbanabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PcbanaboApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcbanaboApplication.class, args);
	}

}
