package com.example.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port =12345;
        System.out.println("Encendido");
        try (ServerSocket serverSocket=new ServerSocket(port)){
            Socket clientSocket =serverSocket.accept();
            System.out.println("Cliente conectado "+clientSocket.getInetAddress());
            BufferedReader in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out =new PrintWriter(clientSocket.getOutputStream(),true);
            String mensaje=in.readLine();
            if(mensaje.equals("Ishak")){
                out.println("Acceso Exitosos");
            }else{
                out.println("Acceso denegado");
            }
            
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
