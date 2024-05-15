package ru.aston.purchases;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PurchasesApplication implements CommandLineRunner {
    @Value("${program.url}")
    private String progUrl;
    @Override
    public void run(String... args) throws Exception {
        log.info("Program started on - {}", progUrl);
    }

    public static void main(String[] args) {
        SpringApplication.run(PurchasesApplication.class, args);
    }

}
