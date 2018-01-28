package es.upm.miw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.upm.miw.services.DatabaseSeederService;

@SpringBootApplication
public class Application implements CommandLineRunner {
    
    @Autowired
    private DatabaseSeederService databaseSeederService;

    // mvn clean spring-boot:run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        this.databaseSeederService.deleteAllAndCreateAdmin();
    }
}
