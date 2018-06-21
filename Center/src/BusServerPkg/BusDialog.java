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
    MessageManager manager;
    String stations;
    int lineNumber;

    public BusDialog(Socket clientSocket, BusServer myServer, MessageManager m)
    {
        manager = m;
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
        String line;
        String station;
        boolean stop=false;
        try
        {
            line = bufferSocketIn.readLine(); //get line number
            lineNumber = Integer.parseInt(line);
            myOutput.printOther("Bus Line: " + lineNumber);
            myOutput.setTitle("Server: Bus " + line);
            stations = manager.BusStartDrive(lineNumber);
            myOutput.printMe("Route: " + stations);
            //Send the stations to the bus
            bufferSocketOut.println(stations);
            //bufferSocketOut.println(this.message.BusStratDrive(line));


            while (true)
            {
                station = bufferSocketIn.readLine();
                if (station == null)
                    break;
                if (station.equals("end"))
                    break;
                myOutput.printOther(station);
                System.out.println("Bus number: " + lineNumber + " arrived to station: " + station  );
                manager.BusArrivedAt(lineNumber,station);
            }
        } catch (IOException e) {}
        finally
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
