
package com.netbows.sensors;

import java.lang.*;

/**
 *
 * @author Argotec
 */
public class Formulas {

    public Formulas() {
    }
    
    
    
    public double getLuxes(double raw){
        
        double luxes = 0.0;
        double v = 0.0;
        
        v = (3.3*raw)/1024;
        luxes = (v - 3.3)*(-297.9);
        
        return luxes;
    }
    
    public double getTemperatureFromThermistor(double raw){
        
        double notRaw = 0.0;
        double NTCResistance = 0.0;
        double temperature = 0.0;
                
        /* Se intenta hacer la cuenta con un try ya que podría haber divisiones entre 0, logaritmos 
                de 0 o menos, etc... */
        try {
            notRaw = raw * (3290.0 / 1024.0);
            NTCResistance = ((notRaw * 10000.0) / (3290.0 - notRaw));
            temperature = (1 / ((1 / (25.0 + 273.15)) + ((1 / 3977.0) * Math.log(NTCResistance / 10000.0)))) - 273.15;
        } 
        catch(Exception ex) {
            System.out.println("Ha fallado la cuenta");
        }
        
        return temperature;
    }
    
}
