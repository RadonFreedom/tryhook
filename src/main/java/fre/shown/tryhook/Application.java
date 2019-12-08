package fre.shown.tryhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static ConfigurableApplicationContext AC;

    public static void main(String[] args) {
        AC = SpringApplication.run(Application.class, args);
    }
}
