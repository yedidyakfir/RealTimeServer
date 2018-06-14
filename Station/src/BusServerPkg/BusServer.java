package BusServerPkg;

import java.io.*;
import java.net.*;

public class BusServer extends Thread
{
    int DEFAULT_PORT = 50000;
    ServerSocket listenSocket;
    Socket clientSockets;

    public BusServer()
    {
        try
        {
            listenSocket = new ServerSocket(DEFAULT_PORT);
        }
        catch (IOException ex)
        {
            System.out.println("Problem creating the server-socket");
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        System.out.println("Server starts on port " + DEFAULT_PORT);
        start();
    }

    public void run()
    {
        try
        {
            while (true)
            {
                clientSockets = listenSocket.accept();
                new BusDialog(clientSockets, this);
            }
        }
        catch (IOException ex)
        {
            System.out.println("Problem listening server-socket");
            System.exit(1);
        }

        System.out.println("end of server");
    }
}
