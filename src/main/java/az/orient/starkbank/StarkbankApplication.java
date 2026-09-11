package az.orient.starkbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "az.orient.starkbank.repository.jpa")
public class StarkbankApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarkbankApplication.class, args);
    }
}
