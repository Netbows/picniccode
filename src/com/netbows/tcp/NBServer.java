package com.netbows.tcp;

import com.netbows.api.NBApi;
import com.netbows.components.NBNetwork;
import com.netbows.components.NBSensor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.netbows.commands.NetbowsCommand;

/**
 *
 * @author Argotec
 */
public class NBServer {

    private static int port = 9756;

    public static void main(String args[]) {
        try {
            final ExecutorService service = Executors.newCachedThreadPool();
            ServerSocket serversock = new ServerSocket(port);
            while (true) {
                Socket socket = serversock.accept();
                service.submit(new MyClass(socket));
            }
        } catch (IOException e) {
        }
    }
}

class MyClass implements Runnable {

    Socket socket;

    public MyClass(Socket s) {
        socket = s;
    }

    public void run() {
        try {
            System.out.println("Esperando eventos...");
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            //Procesamos evento
            String evento = entrada.readLine();
            System.out.println(evento);
            
            //Ejecutamos nuestro código
            NBApi api = new NBApi();
            NetbowsCommand terminal = new NetbowsCommand();
            
            
            //Tu código AQUI
            
            
            
            socket.close();
            
        } catch (IOException e) {
        }
    }

}
