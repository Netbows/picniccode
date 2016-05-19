package com.netbows.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Argotec
 */
public class NetbowsCommand {

    public NetbowsCommand() {

    }

    public String sendCommand(String HOST, String PUERTO, String command) {

        String response = "";
        try {
            Socket socket = new Socket(HOST, (int) Integer.parseInt(PUERTO));
            //conseguimos el canal de entrada
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //conseguimos el canal de salida
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            //Leemos mensaje de bienvenida
            entrada.readLine();
            entrada.readLine();

            salida.println(command);
            response = entrada.readLine();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Cerramos la conexión 
            salida.println("AT!EX");
            socket.close();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("No conectado");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

}
