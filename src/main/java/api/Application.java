package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// @EnableEurekaClient
// @EnableFeignClients("common.feign")
@ComponentScan()
@EnableJpaRepositories()
@EnableCaching
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		System.out.println("Web Starting...");
		SpringApplication.run(Application.class, args);
	}
}
