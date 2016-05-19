
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
public class Main {
    
     public static void main(String[] args) {
         
        NBApi api = new NBApi();
        
        System.out.println("Bienvenidos al Taller de Netbows");
        
        //Configuración de la RED y Servidor al que envía la mi Netbows
        NBNetwork miNetbows = new NBNetwork("192.168.1.163");
       

        
        //Solicitamos la descripción al dispositivo
        System.out.println("Nos comunicamos con la Netbows para pedirle la descripción...");
        Netbows netbows = api.getNetbowsDescription(miNetbows);
        
        System.out.println("Tu Netbows tiene los siguientes módulos conectados:");
        
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
        //Fin de la descripción
     
        
        
      
        //Para procesar los datos de los sensores analógicos
        Formulas formulas = new Formulas();
        
        
        System.out.println();
        
        
        //Pedimos el valor del sensor que hay en el PLUGx. Si no hay SENSOR nos devuelve NO SENSOR CONNECTED
        NBSensor sensor = api.getSensorValue(miNetbows, "PLUG0");
        System.out.println("Sensor:" + sensor.getId() + " presenta el valor:" + sensor.getValue() + " " + sensor.getUnit());
        
         
        //Pedimos el valor del sensor que hay en el PLUGx. Si no hay SENSOR nos devuelve NO SENSOR CONNECTED
        sensor = api.getSensorValue(miNetbows, "PLUG2");
        System.out.println("Sensor:" + sensor.getId() + " presenta el valor:" + sensor.getValue() + " " + sensor.getUnit());
        
	
        //Ejecutamos una acción. Ponemos a ON u OFF el relé
        NBActuator actuator1 = api.doAction(miNetbows, "PLUG1", "ON");
        System.out.println("Actuador:" + actuator1.getId() + " presenta el valor:" + actuator1.getState());
        
        
        NBActuator actuator2 = api.doAction(miNetbows, "PLUG2", "ON");
        System.out.println("Actuador:" + actuator2.getId() + " presenta el valor:" + actuator2.getState());
        
        
     
        
        //Fin demo
        System.out.println("FIN");
		
		
	}
    
}
