import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class StationClient
{
    String SERVERHOST = "LOCALHOST";
//    String SERVERHOST = "";
//    String SERVERHOST = "147.161.23.20";

    int DEFAULT_PORT = 60000;
    Socket clientSocket = null;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    BufferedReader keyBoard;
    StationClientWin myOutput;
    String station;
    String line;
    String[] stations = new String[] {"A","B","C","D"};
    static int s = 0;

    public StationClient()
    {
        this.station = stations[s];
    }

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

            station = JOptionPane.showInputDialog("serial num Station ");
            myOutput = new StationClientWin("Client  ", this);

            // notice about the connection
            myOutput.printMe("Connected to " + clientSocket.getInetAddress() +
                    ":" + clientSocket.getPort());

            while (true)
            {
                myOutput.printMe(this.station);
                line = bufferSocketIn.readLine(); // reads a line from the server
                if (line == null)  // connection is closed ?  exit
                {
                    myOutput.printMe("Connection closed by the Server.");
                    break;
                }
                myOutput.printOther(line); // shows it on the screen
                if (line.equals("end"))
                {
                    break;
                }

            }
        } catch (IOException e)
        {
            myOutput.printMe(e.toString());
            System.err.println(e);
        } finally
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
        StationClient client = new StationClient();
        client.doit();
    }
}
