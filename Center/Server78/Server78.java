

//file name: Server78.java
//Iyar 5770  update Sivan 5778
//Levian Yehonatan
import java.io.*;
import java.net.*;

class Server78 extends Thread 	   //the parallel server
{

    int DEFAULT_PORT = 50000;
    ServerSocket listenSocket;
    Socket clientSockets;

    public Server78()   // constructor of a TCP server
    {
        try
        {
            listenSocket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e)    //error
        {
            System.out.println("Problem creating the server-socket");
            System.out.println(e.getMessage());
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
                new Dialog78(clientSockets, this);
            }

        } catch (IOException e)
        {
            System.out.println("Problem listening server-socket");
            System.exit(1);
        }

        System.out.println("end of server");
    }
}

