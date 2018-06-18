package BusServerPkg;

import java.io.*;
import java.net.Socket;
import Other.MessageManager;

class BusDialog extends Thread // parallel dialogs on the same socket
{

    Socket client;
    BusServer myServer;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    BusDialogWin myOutput;
    MessageManager message;

    public BusDialog(Socket clientSocket, BusServer myServer, MessageManager m)
    {
        message = m;
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
        myOutput = new BusDialogWin("Dialog Win for: " + client.toString(), this);
        start();
    }

    public void run()
    {
        int line;
        String station;
        boolean stop=false;
        try
        {
            line = Integer.parseInt(bufferSocketIn.readLine());
            bufferSocketOut.println(this.message.BusStratDrive(line));

            while (true)
            {
                station = bufferSocketIn.readLine();
                if (station == null)
                    break;
                if (station.equals("end"))
                    break;
                myOutput.printOther(station);
                this.message.BusArrivedAt(line,station);
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
