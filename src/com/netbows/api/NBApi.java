package com.netbows.api;

import com.netbows.components.NBActuator;
import com.netbows.components.NBNetwork;
import com.netbows.components.NBSensor;
import com.netbows.components.Netbows;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import com.netbows.commands.NetbowsCommand;
import com.utils.json.JSONArray;
import com.utils.json.JSONObject;

/**
 *
 * @author Argotec
 */
public class NBApi {

    public NBApi() {
    }

    public void setNetwork(NBNetwork network) {

        String command_config = "AT!CO";
        String length_ssid = "";
        String length_passwd = "";
        String length_server = "";

        String ssid = network.getSSID();
        String passwd = network.getPassword();
        String security = network.getSecurity();
        String ip = network.getIp();
        int port = network.getPort();
        String server = network.getServer();

        if (ssid.length() < 10) {
            length_ssid = "0" + Integer.toString(ssid.length());
        } else {
            length_ssid = Integer.toString(ssid.length());
        }

        if (passwd.length() < 10) {
            length_passwd = "0" + Integer.toString(passwd.length());
        } else {
            length_passwd = Integer.toString(passwd.length());
        }
        if (server.length() < 10) {
            length_server = "0" + Integer.toString(server.length());
        } else {
            length_server = Integer.toString(server.length());
        }

        String command_ssid = command_config + "SS" + length_ssid + ssid;
        String command_passwd = command_config + "PW" + length_passwd + passwd;
        String command_sec = command_config + "SE" + "01" + security;
        String command_server = command_config + "SA" + length_server + server;
        String command_save = command_config + "SN";

        try {
            Socket socket = new Socket(ip, port);
            //conseguimos el canal de entrada
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //conseguimos el canal de salida
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            //Leemos mensaje de bienvenida
            entrada.readLine();
            entrada.readLine();

            salida.println("AT!COMM");

            salida.println(command_ssid);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            salida.println(command_passwd);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            salida.println(command_sec);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            salida.println(command_server);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            salida.println(command_save);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            salida.println("AT!COAM");
            System.out.println("CONEXION CERRADA");
            salida.println("AT!EX");

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("No conectado");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Netbows getNetbowsDescription(NBNetwork miNetwork) {

        NetbowsCommand client = new NetbowsCommand();
        String cadena = "";
        Map<String, String> modules = new HashMap<String, String>();

        cadena = client.sendCommand(miNetwork.getIp(), "9765", "AT!STDE");
        Map<String, Object> data = new HashMap();

        JSONObject json = new JSONObject(cadena);
        String name = json.getString("Name");
        String model = json.getString("model");
        String id = json.getString("Id");

        JSONArray components = json.getJSONArray("Components");
        List<Map<String, String>> plugs = new ArrayList<>();

        Map<String, String> plug = new HashMap<>();

        for (int i = 0; i < components.length(); i++) {
            plug.put("module", components.getJSONObject(i).getString("Module"));
            plug.put("name", components.getJSONObject(i).getString("Name"));
            plug.put("id", components.getJSONObject(i).getString("Id"));
            plugs.add(plug);

            modules.put(components.getJSONObject(i).getString("Module"), components.getJSONObject(i).getString("Id"));
        }

        Netbows netbows = new Netbows(model, name, id, plugs, modules);

        return netbows;
    }

    public Map<String, String> getCompatibleSensors() {

        Map<String, String> sensors = new HashMap<String, String>();

        sensors.put("1", "Temperature");
        sensors.put("2", "SHT_21");
        sensors.put("25", "Temperature");
        sensors.put("26", "Luminosity");
        sensors.put("27", "Power");
        sensors.put("28", "Flowmeter");
        sensors.put("29", "Presence");
        sensors.put("31", "Potentiometer");
        sensors.put("33", "Barometer");
        sensors.put("34", "Current");
        sensors.put("36", "Analog");
        sensors.put("37", "Digital");

        return sensors;
    }

    public String getUnitSensor(String id) {

        Map<String, String> sensors = new HashMap<String, String>();

        sensors.put("1", "Celsius");
        sensors.put("2", "%");
        sensors.put("25", "Celsius");
        sensors.put("26", "Lumens");
        sensors.put("27", "Watts");
        sensors.put("28", "Litres");
        sensors.put("29", "Digital");
        sensors.put("31", "milimetres");
        sensors.put("33", "atm");
        sensors.put("34", "Amperes");
        sensors.put("36", "Raw");
        sensors.put("37", "Raw");

        return sensors.get(id);

    }

    public Map<String, String> getCompatibleActuators() {

        Map<String, String> actuators = new HashMap<String, String>();

        actuators.put("3", "Control_Relay");
        actuators.put("20", "Analog_1_10V");
        actuators.put("21", "Voltage_Regulator");
        actuators.put("22", "Power_Relay");

        return actuators;
    }

    public NBSensor getSensorValue(NBNetwork miNetwork, String plug) {

        NetbowsCommand client = new NetbowsCommand();
        NBSensor sensor = new NBSensor("", "", "");
        Netbows netbows = getNetbowsDescription(miNetwork);
        String id = netbows.getModules().get(plug);
        String command = "";

        if (getCompatibleSensors().containsKey(netbows.getModules().get(plug))) {

            if (Integer.parseInt(id) < 9) {
                command = "AT!SE" + plug.substring(4) + "0" + id;
            } else {
                command = "AT!SE" + plug.substring(4) + id;
            }

            String response = client.sendCommand(miNetwork.getIp(), "9765", command);
            StringTokenizer st = new StringTokenizer(response, ":");
            int tokens = st.countTokens();
            if (tokens > 1) {
                while (st.hasMoreTokens()) {
                    st.nextToken();
                    response = st.nextToken();
                }
            }

            sensor = new NBSensor(getCompatibleSensors().get(id), response, getUnitSensor(id));

        } else {
            sensor = new NBSensor("NO SENSOR CONNECTED", "NO SENSOR CONNECTED", "NO SENSOR CONNECTED");
        }

        return sensor;

    }

    public NBActuator doAction(NBNetwork miNetwork, String plug, String action) {

        NetbowsCommand client = new NetbowsCommand();
        NBActuator actuator = new NBActuator("", "", "");
        Netbows netbows = getNetbowsDescription(miNetwork);
        String id = netbows.getModules().get(plug);
        String command = "";
        String command_state = "";

        if (action.equals("OFF")) {
            action = "OF";
        }

        if (getCompatibleActuators().containsKey(netbows.getModules().get(plug))) {

            if (Integer.parseInt(id) < 9) {
                command = "AT!AC" + plug.substring(4) + "0" + id + action;
                command_state = "AT!AC" + plug.substring(4) + "0" + id + "st";
            } else {
                command = "AT!AC" + plug.substring(4) + id + action;
                command_state = "AT!AC" + plug.substring(4) + id + "st";
            }

            String response = client.sendCommand(miNetwork.getIp(), "9765", command);
            String state = client.sendCommand(miNetwork.getIp(), "9765", command_state);

            StringTokenizer st = new StringTokenizer(state, ":");
            while (st.hasMoreTokens()) {
                st.nextToken();
                state = st.nextToken();
            }

            actuator = new NBActuator(getCompatibleActuators().get(id), response, state);

        } else {
            actuator = new NBActuator("NO ACTUATOR CONNECTED", "NO ACTUATOR CONNECTED", "NO ACTUATOR CONNECTED");
        }

        return actuator;

    }

}
