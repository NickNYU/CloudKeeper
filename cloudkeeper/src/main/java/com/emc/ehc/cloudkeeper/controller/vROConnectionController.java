package com.emc.ehc.cloudkeeper.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emc.ehc.cloudkeeper.model.User;
import com.emc.ehc.cloudkeeper.model.vROConnectionModel;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 18, 2016 4:19:51 PM
* 
*/
@RestController
@RequestMapping(value = "/vRO")
public class vROConnectionController {
    static Map<String, vROConnectionModel> vros = Collections.synchronizedMap(new HashMap<String, vROConnectionModel>());
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<vROConnectionModel> getUserList() {
        List<vROConnectionModel> r = new ArrayList<vROConnectionModel>(vros.values());
        return r;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)//headers = {"content-type=application/json"},
    public String postUser(@RequestBody vROConnectionModel vro) {
        vros.put(vro.getHost(), vro);
        return "success";
    }
    
    @RequestMapping(value = "/{host}", method = RequestMethod.GET)
    public vROConnectionModel getUser(@PathVariable String host) {
        return vros.get(host);
    }
}
