/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netbows.examples;

import com.netbows.api.NBApi;
import com.netbows.commands.NetbowsCommand;
import com.netbows.components.NBActuator;
import com.netbows.components.NBNetwork;
import com.netbows.components.NBSensor;

/**
 *
 * @author Argotec
 */
public class soplar {

    public static void main(String[] args) {

        NBApi api = new NBApi();

        System.out.println("Bienvenidos al Taller de Netbows");

        //Configuración de la RED y Servidor al que envía la mi Netbows
        NBNetwork miNetbows = new NBNetwork("192.168.1.166");

        NBNetwork netbowsReles = new NBNetwork("192.168.1.101");
        NetbowsCommand terminal = new NetbowsCommand();
        
        int i = 1;
        while (true) {

            //Pedimos el valor del sensor que hay en el PLUGx. Si no hay SENSOR nos devuelve NO SENSOR CONNECTED
            NBSensor sensor = api.getSensorValue(miNetbows, "PLUG0");
            System.out.println("Sensor:" + sensor.getId() + " presenta el valor:" + sensor.getValue() + " " + sensor.getUnit());

            Double valueSensor = Double.parseDouble(sensor.getValue());

            if (valueSensor > 0.20 && valueSensor < 0.4) {
                terminal.sendCommand("192.168.1.101", "9765", "AT!AC1355ON");
                
            }
            if (valueSensor > 0.4 && valueSensor < 0.6) {
                terminal.sendCommand("192.168.1.101", "9765", "AT!AC3355ON");
            }
            if (valueSensor > 0.6 && valueSensor < 0.8) {
                terminal.sendCommand("192.168.1.101", "9765", "AT!AC5355ON");
            }
            if (valueSensor > 1.0 && valueSensor < 1.20) {
                terminal.sendCommand("192.168.1.101", "9765", "AT!AC8355ON");
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
