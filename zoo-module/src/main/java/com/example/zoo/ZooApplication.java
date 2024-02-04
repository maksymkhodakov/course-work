package com.example.zoo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.TimeZone;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ZooApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZooApplication.class, args);
    }

    @PostConstruct
    private void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
