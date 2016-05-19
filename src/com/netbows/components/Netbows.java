
package com.netbows.components;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Argotec
 */
public class Netbows {
    
    private String model;
    private String name;
    private String id;
    
    private List<Map<String, String>> components;
    private Map<String, String> modules;

    public Netbows(String model, String name, String id, List<Map<String, String>> components, Map<String, String> modules) {
        this.model = model;
        this.name = name;
        this.id = id;
        this.components = components;
        this.modules = modules;
    }

   

    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, String>> getComponents() {
        return components;
    }

    public void setComponents(List<Map<String, String>> components) {
        this.components = components;
    }

    public Map<String, String> getModules() {
        return modules;
    }

    public void setModules(Map<String, String> modules) {
        this.modules = modules;
    }


    
}
