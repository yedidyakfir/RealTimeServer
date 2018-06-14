package StationServerPkg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StationServer extends Thread
{
    int DEFAULT_PORT = 60000;
    ServerSocket listenSocket;
    Socket clientSockets;

    public StationServer()
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
                new StationDialog(clientSockets, this);
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
