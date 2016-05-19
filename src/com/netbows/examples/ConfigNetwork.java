
package com.netbows.examples;

import com.netbows.api.NBApi;
import com.netbows.components.NBNetwork;

/**
 *
 * @author Argotec
 */

public class ConfigNetwork {

    public ConfigNetwork() {
    }
    
    
    
    
    public void configuraWIFI(NBNetwork miNetbows){
        
        NBApi api = new NBApi();
        
        //Configuramos los parámetros de conexión a la Netbows
        api.setNetwork(miNetbows);
        
    }
    
    public static void main(String[] args) {
        
        ConfigNetwork init = new ConfigNetwork();
        
        NBNetwork miNetbows = new NBNetwork();
        miNetbows.setIp("192.168.1.161");
        miNetbows.setSSID("Netbows");
        miNetbows.setPassword("netbows765");
        miNetbows.setPort(9765);
        miNetbows.setSecurity("B");
        miNetbows.setServer("192.168.1.150");
        
        init.configuraWIFI(miNetbows);
    }
    
}
