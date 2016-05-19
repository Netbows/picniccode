package com.netbows.components;

/**
 *
 * @author Argotec
 */
public class NBActuator {
    
    private String id;
    private String state;
    private String response;

    public NBActuator(String id, String response, String state) {
        this.id = id;
        this.state = state;
        this.response = response;
    }

 

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    
}
