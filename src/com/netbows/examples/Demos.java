
package com.netbows.examples;

import com.netbows.api.NBApi;
import com.netbows.components.NBActuator;
import com.netbows.components.NBNetwork;
import com.netbows.components.NBSensor;
import com.netbows.components.Netbows;
import com.netbows.sensors.Formulas;

/**
 *
 * @author Argotec
 */
public class Demos {
    
    public static void main(String[] args) {
         
        //TU CÓDIGO AQUI 
		
        NBApi api = new NBApi();
        
        System.out.println("Bienvenidos al Taller de Netbows");
        
        //Configuración de la RED y Servidor al que envía la mi Netbows
        NBNetwork miNetbows = new NBNetwork("192.168.1.206");
       

        //Solicitamos la descripción al dispositivo
        System.out.println("Nos comunicamos con la Netbows para pedirle la descripción...");
        Netbows netbows = api.getNetbowsDescription(miNetbows);
        
        System.out.println("Tu Netbows: " + netbows.getName() +  "tiene los siguientes módulos conectados:");
        
        for(int i=0;i<4;i++){
        //PLUGS
        if(netbows.getModules().containsKey("PLUG" + i)){
           String id = netbows.getModules().get("PLUG" + i);
           //¿Es un sensor?
           if(api.getCompatibleSensors().containsKey(id)){
               System.out.println("PLUG" + i + " SENSOR: " + api.getCompatibleSensors().get(id)+ " CONECTADO");
           }
           //Es un actuador
           else{
               System.out.println("PLUG" + i +  " ACTUADOR: " + api.getCompatibleActuators().get(id)+ " CONECTADO");
           }
        }
            
        }
     
      
	
        //Ejecutamos una acción. Ponemos a ON u OFF el relé
        NBActuator actuator = api.doAction(miNetbows, "PLUG3", "ON");
        
        
        //Fin demo
        System.out.println("FIN");
		
		
	}
    
}
