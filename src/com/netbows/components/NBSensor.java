
package com.netbows.components;

/**
 *
 * @author Argotec
 */
public class NBSensor {
    
    private String id;
    private String value;
    private String unit;

    public NBSensor(String id, String value, String unit) {
        this.id = id;
        this.value = value;
        this.unit = unit;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    
}
