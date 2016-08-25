package com.emc.ehc.cloudkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.ehc.cloudkeeper.model.ZookeeperServer;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 25, 2016 4:45:00 PM
* 
*/
@Service
public class ZookeeperService {
    
    @Autowired
    private ZookeeperServer zkServer;
    
    public String getZkServerHostPort() {
        return zkServer.getHost() + ":" + zkServer.getPort();
    }
}
