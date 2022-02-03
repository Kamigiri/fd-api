package com.keraisoft.fd;

import com.keraisoft.fd.csv.FdCsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(IssueRepository repository) throws IOException {
        FdCsvReader reader = new FdCsvReader();
        List<List<String>> results = reader.getRecords();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy", Locale.US);

        return args -> {
            for (int i= 0; i < results.size(); i++) {
                if( i == 0) {
                    continue;
                }
                repository.save(new Issue(results.get(i).get(0),results.get(i).get(1), BigDecimal.valueOf(Double.parseDouble(results.get(i).get(2))), results.get(i).get(3), formatter.parse(results.get(i).get(4) + results.get(i).get(5))));
            }
            repository.findAll().forEach(issue -> log.info("Preloaded " + issue));
        };
    }

}
