package com.emc.ehc.cloudkeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emc.ehc.cloudkeeper.model.ZookeeperServer;
import com.emc.ehc.cloudkeeper.service.ZookeeperService;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 25, 2016 4:46:48 PM
* 
*/

@RestController
public class ZookeeperController {
    
    @Autowired
    private ZookeeperService zkService;
    
    @RequestMapping(value="/zookeeper", method = RequestMethod.GET)
    public String getZkInfo() {
        return zkService.getZkServerHostPort();
    }
}
