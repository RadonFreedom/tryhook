package fre.shown.tryhook.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "fre.shown.tryhook")
@SpringBootApplication
public class TryhookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TryhookApplication.class, args);
    }

}
