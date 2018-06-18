package StationServerPkg;

import java.io.*;
import java.net.Socket;

import Other.Event64;
import Other.MessageManager;

public class StationDialog extends Thread
{
    Socket client;
    StationServer myServer;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    StationDialogWin myOutput;
    MessageManager messageManager;
    Event64 busArrivedEv;

    public StationDialog(Socket clientSocket, StationServer myServer, MessageManager messageManager)
    {
        this.busArrivedEv = new Event64();
        this.messageManager = messageManager;
        client = clientSocket;
        this.myServer = myServer;
        try
        {
            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException e)
        {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
            System.err.println("server:Exception when opening sockets: " + e);
            return;
        }
        myOutput = new StationDialogWin("Dialog Win for: " + client.toString(), this);
        start();
    }

    public void run()
    {
        String line;
        boolean stop=false;
        try
        {
            line = bufferSocketIn.readLine();
            messageManager.AddStation(line,this.busArrivedEv);
            myOutput.printOther(line);
            while (true)
            {
                bufferSocketOut.println((String)this.busArrivedEv.waitEvent());
            }
        } catch (IOException e)
        {
        } finally
        {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
        }

        myOutput.printMe("end of  dialog ");
        myOutput.send.setText("Close");

    }

    void exit()
    {
        try
        {
            client.close();
        } catch (IOException e2)
        {
        }
    }
}
