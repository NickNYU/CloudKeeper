package com.emc.ehc.cloudkeeper;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by NickNYU on 8/24/2016.
 */
@SpringBootApplication
@EnableAsync
public class Application {
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
