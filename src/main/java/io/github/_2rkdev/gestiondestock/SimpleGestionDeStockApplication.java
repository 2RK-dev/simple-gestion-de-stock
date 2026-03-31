package io.github._2rkdev.gestiondestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SimpleGestionDeStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleGestionDeStockApplication.class, args);
    }

}
