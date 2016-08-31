package com.emc.ehc.cloudkeeper.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emc.ehc.cloudkeeper.factory.RestClientFactory;
import com.emc.ehc.cloudkeeper.model.Vro;
import com.emc.ehc.cloudkeeper.response.VroResponse;
import com.emc.ehc.cloudkeeper.response.VroStatusResponse;
import com.emc.ehc.cloudkeeper.service.ZookeeperService;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 26, 2016 11:53:35 AM
 * 
 */
@RestController
@RequestMapping(value = "/vRO")
public class VroController {

    private static final Logger logger = LoggerFactory.getLogger(VroController.class);

    @Autowired
    private ZookeeperService zkService;
    private Client restClient = RestClientFactory.createClient();
    private AtomicInteger counter = new AtomicInteger(0);

    @Value("${vro.path}")
    private String basePath;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public VroResponse setVro(@RequestBody Vro vro) {
        String path = basePath + "/" + vro.getName();
        try {
            zkService.setData(path, vro);
            zkService.registerVroWatcher(vro);
            return new VroResponse(vro.getName(), vro.getHost(), true);
        } catch (Exception e) {
            return new VroResponse(vro.getName(), vro.getHost(), false);
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Vro getVro(@PathVariable String name) {
        String path = basePath + "/" + name;
        try {
            Vro vro = zkService.getData(path);
            return vro;
        } catch (Exception e) {
            logger.error("vRO Controller fail to get vRO : " + name);
            return null;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Vro> getVros() {
        List<String> childrenPath;
        try {
            childrenPath = zkService.getChildrenPath(basePath);
            List<Vro> vros = new ArrayList<Vro>();
            for (String path : childrenPath) {
                vros.add(zkService.getData(basePath + "/" + path));
            }
            return vros;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            return new ArrayList<Vro>();
        }
    }

    @RequestMapping(value = "/{name}/status", method = RequestMethod.GET)
    public VroStatusResponse getVroStatus(@PathVariable String name) {
        try {
            // Get vRO object, and retrieve its status by rest api
            String path = basePath + "/" + name;
            Vro vro = zkService.getData(path);
            boolean status = getVroHealth(vro);

            // Set status to zookeeper server
            String statusPath = path + "/status";
            String statusString = status ? "true" : "false";
            System.out.println(statusString);
            if (!status) {
                if (counter.compareAndSet(0, 1)) {
                    System.out.println(counter.get());
                    zkService.setData(statusPath, "false");
                }
            } else {
                System.out.println(counter.get());
                zkService.setData(statusPath, "true");
                counter.compareAndSet(1, 0);
            }

            return new VroStatusResponse(name, vro.getHost(), status);
        } catch (Exception e) {
            return new VroStatusResponse(name, null, false);
        }
    }

    private boolean getVroHealth(Vro vro) {

        try {
            // VRO health status API
            String hostPort = vro.getHost() + ":8281";
            Response response = restClient.target("https://" + hostPort + "/vco/api/about")
                    .request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
