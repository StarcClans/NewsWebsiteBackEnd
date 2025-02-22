package lt.ca.javau11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsBackendApplication.class, args);
    }
}