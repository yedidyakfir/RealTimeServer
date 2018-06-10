

//file name: Dialog78.java
//Iyar 5770 update Sivan 5778
//Levian Yehonatan
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Dialog78 extends Thread // parallel dialogs on the same socket
{

    Socket client;
    Server78 myServer;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    DialogWin78 myOutput;

    public Dialog78(Socket clientSocket, Server78 myServer)
    {
        client = clientSocket;
        this.myServer = myServer;
        try
        {
            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(
                    new InputStreamReader(
                    clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(
                    new BufferedWriter(
                    new OutputStreamWriter(
                    clientSocket.getOutputStream())), true);
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
        myOutput = new DialogWin78("Dialog Win for: " + client.toString(), this);
        start();
    }

    public void run()
    {
        String line;
        boolean stop=false;
        try
        {
            while (true)
            {
                line = bufferSocketIn.readLine();
                if (line == null)
                    break;
                if (line.equals("end"))
                    break;
                myOutput.printOther(line);
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
