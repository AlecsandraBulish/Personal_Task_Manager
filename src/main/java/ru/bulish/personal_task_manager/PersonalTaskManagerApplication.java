package ru.bulish.personal_task_manager;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonalTaskManagerApplication {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5433/manager", "postgres", "bulish")
                .load();
        flyway.migrate();
        SpringApplication.run(PersonalTaskManagerApplication.class, args);
    }

}
