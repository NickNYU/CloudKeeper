package com.emc.ehc.cloudkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emc.ehc.cloudkeeper.controller.UserController;
import com.emc.ehc.cloudkeeper.model.User;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
