package BusServerPkg;

import Other.MessageManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BusServer extends Thread
{
    int DEFAULT_PORT = 50000;
    ServerSocket listenSocket;
    Socket clientSockets;
    MessageManager messageManager;

    public BusServer(MessageManager messageManager)
    {
        this.messageManager = messageManager;
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
                new BusDialog(clientSockets, this,messageManager);
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
