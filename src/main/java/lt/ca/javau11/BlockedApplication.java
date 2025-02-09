package lt.ca.javau11;

import lt.ca.javau11.service.SectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlockedApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockedApplication.class, args);
    }
     @Bean
    public CommandLineRunner commandLineRunner(SectionService sectionService) {
        return args -> {
            sectionService.fetchAndSaveSections();
        };
    }
}