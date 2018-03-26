/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 * @author Vibhavi
 */
public class ConfigSettings {

    private Properties prop = new Properties();
    private OutputStream output = null;

    public void GenarateConfig(HashMap<String, String> properties[]) {
        try {

            output = new FileOutputStream("dist//config.properties");

            for (HashMap map : properties) {
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry pair = (HashMap.Entry) it.next();
                  //  System.out.println(pair.getKey() + " = " + pair.getValue());
                    prop.setProperty(pair.getKey().toString(),pair.getValue().toString());
                    
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
         
            prop.store(output, null);
        } catch (IOException ex) {

        }finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
