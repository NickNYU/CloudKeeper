package com.emc.ehc.cloudkeeper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.emc.ehc.cloudkeeper.model.Vro;
import com.emc.ehc.cloudkeeper.service.ZookeeperService;

/**
 * Created by NickNYU on 8/24/2016.
 */
@SpringBootApplication
public class Application {
    
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        
    }
}
