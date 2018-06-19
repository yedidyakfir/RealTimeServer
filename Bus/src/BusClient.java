import java.io.*;
import java.net.*;


public class BusClient
{
    String SERVERHOST = "LOCALHOST";

    int DEFAULT_PORT = 50000;
    Socket clientSocket = null;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    BufferedReader keyBoard;
    BusClientWin myOutput;
    String line = "5";
    String[] stations;

    public void doit()
    {
        try
        {
            // request to server
            clientSocket = new Socket(SERVERHOST, DEFAULT_PORT);

            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream())), true);


//  	   Init streams to read text from the keyboard
//	   keyBoard = new BufferedReader(
//	   new InputStreamReader(System.in));


            myOutput = new BusClientWin("Client  ", this);

            // notice about the connection
            myOutput.printMe("Connected to " + clientSocket.getInetAddress() +
                    ":" + clientSocket.getPort());

            //send line number
            bufferSocketOut.println(line);

            while (true)
            {
                String temp = bufferSocketIn.readLine(); // reads a line from the server
                stations = temp.split(" "); //reads the stations from the server
                if (stations == null)  // connection is closed ?  exit
                {
                    myOutput.printMe("Connection closed by the Server.");
                    break;
                }
                myOutput.printOther(stations.toString()); // shows it on the screen
                if (line.equals("end"))
                {
                    break;
                }
                for (String station : stations) //Bus Runs Through stations
                {
                    Thread.sleep(5000); //simulates a driving bus
                    bufferSocketOut.println(" " + line + " " + station );
                    myOutput.printMe("Got to station " + station);
                }
            }
        } catch (IOException e)
        {
            myOutput.printMe(e.toString());
            System.err.println(e);
        }
        catch (InterruptedException e)
        {
            myOutput.printMe(e.toString());
            System.err.println(e);
        }
        finally
        {
            try
            {
                if (clientSocket != null)
                {
                    clientSocket.close();
                }
            } catch (IOException e2)
            {
            }
        }
        myOutput.printMe("end of client ");
        myOutput.send.setText("Close");

        System.out.println("end of client ");
    }

    public static void main(String[] args)
    {
        BusClient client = new BusClient();
        client.doit();
    }
}
