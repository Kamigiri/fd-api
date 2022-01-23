package com.keraisoft.fd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(IssueRepository repository) {

        return args -> {
            repository.save(new Issue("Test","Haustier", BigDecimal.valueOf(9.99), "Amazon",new Date()));
            repository.save(new Issue("Test2","Haustier", BigDecimal.valueOf(19.99), "Amazon",new Date()));
            repository.findAll().forEach(issue -> log.info("Preloaded " + issue));
        };
    }
}
