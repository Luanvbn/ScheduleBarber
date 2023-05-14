package br.com.schedulebarber.scheduleBarber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaRepositories
public class ScheduleBarberApplication {

	public static void main(String[] args) {

		SpringApplication.run(ScheduleBarberApplication.class, args);
	}

}
