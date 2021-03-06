package com.emc.ehc.cloudkeeper.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 22, 2016 10:42:51 AM
* 
*/
public class Object2ByteUtils {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream inputStream = new ObjectInputStream(in);
        return inputStream.readObject();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
