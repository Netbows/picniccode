
package com.netbows.components;

/**
 *
 * @author Argotec
 */
public class NBNetwork {
    
    private String SSID;
    private String password;
    private String security;
    private String server;
    private String ip;
    private int port;

    public NBNetwork() {
    }

    public NBNetwork(String ip) {
        this.ip = ip;
    }
    
    

    
    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    
    
    
    
}
